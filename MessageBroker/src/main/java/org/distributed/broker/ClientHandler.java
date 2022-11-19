package org.distributed.broker;

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
    private final Condition queueNotFull = qLock.newCondition();
    private final Condition queueNotEmpty = qLock.newCondition();

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
        qLock.lock();
        this.webSocket = webSocket;
        this.state = ClientState.ACTIVE;
        queueNotFull.signalAll();
        qLock.unlock();
    }

    public void removeWebSocket() {
        qLock.lock();
        this.webSocket = null;
        this.state = ClientState.INACTIVE;
        qLock.unlock();
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
        webSocket.send(userMessage.toString());
        if(userMessage.getMessageType() == MessageType.USER_LOGOUT_SUCCESSFUL || userMessage.getMessageType() == MessageType.USER_LOGIN_FAIL) {
            removeWebSocket();
        }
    }

    public void run() {
        while(!stop) {
            qLock.lock();
            try{
                while (messageQueue.size() ==0 || this.state == ClientState.INACTIVE) {
                    queueNotFull.await();
                }

                ChatMessage msg = (ChatMessage) messageQueue.element();
                //TODO: Convert the message into Socket response format.
                if(webSocket != null) {
                    webSocket.send(String.valueOf(msg).getBytes());
                    messageQueue.poll();
                }

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public void stop() {
        stop = true;
    }





}
