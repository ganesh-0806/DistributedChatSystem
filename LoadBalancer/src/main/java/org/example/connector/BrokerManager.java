package org.example.connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.loadBalancer.LoadBalancer;
import org.example.model.ChatMessage;
import org.example.model.Message;
import org.example.model.UserMessage;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

import static org.example.model.MessageType.ADD_USER;

public class BrokerManager extends WebSocketServer {

    private ServerSocket loadSocket;
    private ObjectOutputStream loadOutputStream;
    //TODO: Singleton
    public BrokerManager(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println("New connection from " + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {

    }

    @Override
    public void onMessage(WebSocket webSocket, String request) {
        // TODO: pass it as serialized string stream
        ObjectMapper mapper = new ObjectMapper();
        try {
            Message msg = mapper.readValue(request, Message.class);
            UserMessage userMsg;
            ChatMessage chatMsg;
            switch (msg.getMessageType()) {
                case ADD_USER:
                case USER_LOGOUT:
                    userMsg = (UserMessage) msg;
                    //handleAuth(webSocket, userMsg);
                    //pingLoadBalancer(userMsg);
                    break;
                case TEXT_MESSAGE:
                    chatMsg = (ChatMessage) msg;
                    //handleConversation(webSocket, chatMsg);
                    //pingLoadBalancer(chatMsg);
                    break;
            }
            System.out.println("Message from user: " + msg.getFromUser() + ", type:" + msg.getMessageType());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {

    }

    @Override
    public void onStart() {
        System.out.println("Web Server Socket started...");
        //TODO: Get the load balancer IP
        InetAddress host;
        try {
            host = InetAddress.getLocalHost();
            loadSocket = new ServerSocket(9876);
            //loadOutputStream = (ObjectOutputStream) loadSocket.getOutputStream();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
/*
    private void handleAuth(WebSocket webSocket, UserMessage userMessage){
        try {
            ClientCache clientCache = ClientCache.getInstance();
            ClientHandler clientHandler = clientCache.getClient(userMessage.getFromUser().getUserName());
            if(clientHandler == null) {
                clientCache.addClient(userMessage.getFromUser().getUserName(), new ClientHandler(userMessage.getFromUser(), webSocket));
            }
            else {
                clientHandler.setWebSocket(webSocket);
            }
            loadOutputStream.writeChars(userMessage.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleConversation(WebSocket webSocket, ChatMessage chatMessage){
        try {
            loadOutputStream.writeChars(chatMessage.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/
}
