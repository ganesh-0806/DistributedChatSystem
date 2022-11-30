package org.distributed.broker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.distributed.model.*;
import org.java_websocket.WebSocket;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

enum ClientState {
    ACTIVE, INACTIVE
}

public class ClientHandler implements Runnable {

    private static final int CAPACITY = 1000;
    private volatile boolean stop = false;
    private ClientState state;
    private User user;
    private OutputStream outputStream;
    private WebSocket webSocket;
    private Queue<Message> messageQueue;
    private final Lock qLock = new ReentrantLock();
    private final Lock sockLock = new ReentrantLock();
    private final Condition queueNotFull = qLock.newCondition();
    private final Condition queueNotEmpty = qLock.newCondition();
    private final Condition sockActive = sockLock.newCondition();

    public ClientHandler(User user) {
        this.user = user;
        state = ClientState.INACTIVE;
        messageQueue = new LinkedList<Message>();
    }

    public ClientHandler(User user, WebSocket webSocket) {
        this.user = user;
        this.webSocket = webSocket;
        state = ClientState.ACTIVE;
        messageQueue = new LinkedList<Message>();
    }

    public void setWebSocket(WebSocket webSocket) {
        sockLock.lock();
        this.webSocket = webSocket;
        this.state = ClientState.ACTIVE;
        sockActive.signalAll();
        sockLock.unlock();
    }

    public void removeWebSocket() {
        sockLock.lock();
        this.webSocket.close();
        this.webSocket = null;
        this.state = ClientState.INACTIVE;
        sockLock.unlock();
    }

    public void addMessage(ChatMessage msg) {
        qLock.lock();
        try {
            while (messageQueue.size() == CAPACITY) {
                queueNotEmpty.await();
            }
            messageQueue.offer(msg);
            queueNotFull.signalAll();

        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        finally {
            qLock.unlock();
        }
    }

    public void sendAuthResponse(UserMessage userMessage) {
        //TODO: verify json stringify
        ObjectMapper mapper = new ObjectMapper();
        try {
            String messageJson = mapper.writeValueAsString(userMessage);
            sockLock.lock();
            webSocket.send(messageJson);
            sockLock.unlock();
            if(userMessage.getType() == MessageType.USER_LOGOUT_SUCCESSFUL || userMessage.getType() == MessageType.USER_LOGIN_FAIL) {
                removeWebSocket();
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendFriendResponse(FriendMessage friendMessage) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String messageJson = mapper.writeValueAsString(friendMessage);
            sockLock.lock();
            webSocket.send(messageJson);
            sockLock.unlock();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        ObjectMapper mapper = new ObjectMapper();
        while(!stop) {
            qLock.lock();
            try{
                while (messageQueue.size() ==0)  {
                    queueNotFull.await();
                }

                sockLock.lock();
                while( this.state == ClientState.INACTIVE) {
                    sockActive.await();
                }

                ChatMessage msg = (ChatMessage) messageQueue.element();

                if(webSocket != null) {
                    String messageJson = mapper.writeValueAsString(msg);
                    webSocket.send(messageJson);
                    messageQueue.poll();
                }

                queueNotEmpty.signalAll();

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            finally {
                sockLock.unlock();
                qLock.unlock();
            }

        }
    }
    public void stop() {
        stop = true;
    }





}
