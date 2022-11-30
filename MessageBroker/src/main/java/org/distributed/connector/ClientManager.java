package org.distributed.connector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.distributed.broker.ClientCache;
import org.distributed.broker.ClientHandler;
import org.distributed.model.*;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientManager extends WebSocketServer {

    private Socket loadSocket;
    private ObjectOutputStream loadOutputStream;
    //TODO: Singleton
    public ClientManager(int port) {
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
        //System.out.println("Message received" + request);
        // TODO: pass it as serialized string stream
        ObjectMapper mapper = new ObjectMapper();
        //UserMessage ab = new UserMessage(new User("ab"), "ab", MessageType.ADD_USER);

        //String temp = "{\"fromUser\":\"abc\",\"password\":\"abc\",\"type\":\"ADD_USER\"}";

        try {
            //System.out.println(mapper.writeValueAsString(ab));
            //TODO: verify json to object conversion
            //MessageType abc = mapper.readValue(temp, MessageType.class);
            System.out.println("Message received" + request);
            Message msg = mapper.readValue(request, Message.class);
            //UserMessage abcmsg = mapper.readValue(request, UserMessage.class);
            System.out.println("Message received  as ");
            System.out.println(msg.getFromUser().getUserName() + " " + msg.getType());
            UserMessage userMsg;
            ChatMessage chatMsg;
            FriendMessage friendMsg;
            switch (msg.getType()) {
                case ADD_USER:
                case USER_LOGOUT:
                    System.out.println("In  Switch" + request);
                    userMsg = mapper.readValue(request, UserMessage.class);;
                    handleAuth(webSocket, userMsg);
                    break;
                case ADD_FRIEND_REQUEST:
                case GET_FRIENDS_REQUEST:
                    friendMsg = mapper.readValue(request, FriendMessage.class);;
                    handleFriends(webSocket, friendMsg);
                case TEXT_MESSAGE:
                    chatMsg = mapper.readValue(request, ChatMessage.class);;;
                    handleConversation(webSocket, chatMsg);
                    break;
            }
            System.out.println("Message from user: " + msg.getFromUser() + ", type:" + msg.getType());
        } catch (JsonProcessingException e) {
            System.out.println("error with exception " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        //TODO: get associated client handler and remove the web socket
    }

    @Override
    public void onStart() {
        System.out.println("Web Server Socket started...");

        //TODO: Get the load balancer IP

        try {
            //InetAddress host = InetAddress.getByName("54.157.162.179");
             //host = InetAddress.getLocalHost();
             loadSocket = new Socket("54.157.162.179", 8081);
             loadOutputStream = (ObjectOutputStream) loadSocket.getOutputStream();
        } catch (UnknownHostException e) {
            System.out.println("Error at connecting to load balancer");
        } catch (IOException e) {
            System.out.println("Error at retrieving the output stream");
        }

    }

    private void handleAuth(WebSocket webSocket, UserMessage userMessage){
        System.out.println(" in Auth" + userMessage.getFromUser().getUserName() + " " + userMessage.getPassword());
        /*ObjectMapper mapper = new ObjectMapper();
        try {
            webSocket.send(mapper.writeValueAsString(new UserMessage(userMessage.getFromUser(), "", "", MessageType.USER_LOGIN_SUCCESSFUL)));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }*/
        try {
            ClientCache clientCache = ClientCache.getInstance();
            ClientHandler clientHandler = clientCache.getClient(userMessage.getFromUser().getUserName());
            if(clientHandler == null) {
                clientHandler = new ClientHandler(userMessage.getFromUser(), webSocket);
                clientCache.addClient(userMessage.getFromUser().getUserName(), clientHandler);
            }
            else {
                clientHandler.setWebSocket(webSocket);
            }
            loadOutputStream.writeObject(userMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleFriends(WebSocket webSocket, FriendMessage friendMessage){
        try {
            loadOutputStream.writeObject(friendMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleConversation(WebSocket webSocket, ChatMessage chatMessage){
        try {
            loadOutputStream.writeObject(chatMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
