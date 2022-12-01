package org.distributed.connector;

import org.distributed.fd.ServerCache;
import org.distributed.model.*;
import org.distributed.db.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import static org.distributed.model.MessageType.*;

public class BalancerHandler implements Runnable {
    private Socket socket;
    private Message msg;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private int port;
    private BrokerHandler brokerHandler;
    private MongoConnection mongoConnection;

    public BalancerHandler(int port) {
        InetAddress host;
        this.port = port;
        try {
            mongoConnection= MongoConnection.getInstance();
            host = InetAddress.getLocalHost(); //100.25.204.112
            socket = new Socket("127.0.0.1", this.port);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.brokerHandler = BrokerHandler.getInstance();
            //outputStream = (ObjectOutputStream) socket.getOutputStream();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(Message msg){
        try {
            outputStream.writeObject(msg);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            while (true) {
            //TODO: typecast read input into message
                System.out.println("Before read");
                msg = (Message) inputStream.readObject();
                System.out.println("After read ");
                System.out.println(msg);
                if (msg.getMessageType() == TEXT_MESSAGE) {
                    ChatMessage msg1 = (ChatMessage) msg;
                    brokerHandler.send(msg1);
                } else if (msg.getMessageType() == USER_LOGOUT) {
                    UserMessage msg1 = (UserMessage) msg;
                    msg1.setMessageType(USER_LOGOUT_SUCCESSFUL);
                    brokerHandler.send(msg1);
                } else if (msg.getMessageType() == ADD_USER) {
                    UserMessage msg1 = (UserMessage) msg;
                    //1. check if user exists in db
                    //if no, add user and return USER_LOGIN_SUCCESSFUL
                    //else, return USER_LOGIN_FAIL
                    if (mongoConnection.isUserExists(msg.getFromUser().getUserName())) {
                        if (mongoConnection.isUserValid(msg.getFromUser().getUserName(), msg1.getPassword())) {
                            msg1.setPassword("");
                            msg1.setMessageType(USER_LOGIN_SUCCESSFUL);
                        } else {
                            msg1.setPassword("");
                            msg1.setDescription("No such user exists");
                            msg1.setMessageType(USER_LOGIN_FAIL);
                        }
                    } else {
                        //TODO: Assuming this wont fail
                        mongoConnection.addUser(msg1.getFromUser().getUserName(), msg1.getPassword());
                        msg1.setMessageType(USER_LOGIN_SUCCESSFUL);
                        msg1.setPassword("");
                    }
                    brokerHandler.send(msg1);
                }
                else if (msg.getMessageType() == ADD_FRIEND_REQUEST) {

                    FriendMessage msg1 = (FriendMessage) msg;
                    User user2 = msg1.getFriends().get(0);

                    System.out.println("Add friend request from " + msg.getFromUser().getUserName() + " to "+user2.getUserName());

                    if(mongoConnection.isUserExists(msg1.getFromUser().getUserName()) && mongoConnection.isUserExists(user2.getUserName())) {
                        mongoConnection.addFriend(msg1.getFromUser().getUserName(), user2.getUserName());
                        msg1.setMessageType(ADD_FRIEND_SUCCESSFUL);
                    }
                    else {
                        msg1.setMessageType(ADD_FRIEND_FAIL);
                        msg1.setDesc("User does not exist");
                    }

                    brokerHandler.send(msg1);
                } else if (msg.getMessageType() == SERVER_EXITED) {
                    ServerCache serverCache = ServerCache.getInstance();
                    ServerMessage msg1 = (ServerMessage) msg;
                    serverCache.removeIp(msg1.getMessageContent());
                }else {
                    System.out.println("Get friends request " + msg.getFromUser().getUserName());
                    //msg.getMessageType() = GET_FRIENDS_REQUEST
                    FriendMessage msg1 = (FriendMessage) msg;
                    //TODO: Get friends from db
                    ArrayList<User> friends = mongoConnection.retrieveFriends(msg1.getFromUser().getUserName());
                    for (User f : friends) {
                        msg1.addFriend(f);
                    }
                    msg1.setMessageType(GET_FRIENDS_SUCCESSFUL);
                    brokerHandler.send(msg1);
                }
            }
        }
        catch (IOException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
