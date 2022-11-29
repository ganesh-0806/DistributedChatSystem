package org.distributed.connector;

import org.distributed.fd.ServerCache;
import org.distributed.model.*;

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

    public BalancerHandler(int port) {
        InetAddress host;
        this.port = port;
        try {
            host = InetAddress.getLocalHost();
            socket = new Socket(host, this.port);
            inputStream = (ObjectInputStream) socket.getInputStream();
            outputStream = (ObjectOutputStream) socket.getOutputStream();
            this.brokerHandler = BrokerHandler.getInstance();
            //outputStream = (ObjectOutputStream) socket.getOutputStream();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void send(Message msg){
        try {
            outputStream.writeObject(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            //TODO: typecast read input into message
            try {
                msg = (Message) inputStream.readObject();

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
                    if (db.contains(msg1.getUser().getUserName())) {
                        if(db.check(msg1.getUser().getUserName(), msg1.getPassword())) {
                            msg1.setMessageType(USER_LOGIN_SUCCESSFUL);
                        }
                        else {
                            msg1.setMessageType(USER_LOGIN_FAIL);
                        }
                    } else {
                        // TODO: insert in db
                        msg1.setMessageType(USER_LOGIN_SUCCESSFUL);
                    }
                    brokerHandler.send(msg1);
                } else if (msg.getMessageType() == ADD_FRIEND_REQUEST) {
                    FriendMessage msg1 = (FriendMessage) msg;
                    User user2 = msg1.getFriends().get(0);
                    //1. check if user has the friend added already
                    //if no, add user to friend list and return ADD_FRIEND_SUCCESSFUL msg
                    //else, return ADD_FRIEND_FAIL msg
                    if (db.add(msg1.getFromUser().getUserName(), user2.getUserName())) {
                        msg1.setMessageType(ADD_FRIEND_SUCCESSFUL);
                    } else {
                        msg1.setMessageType(ADD_FRIEND_FAIL);
                    }
                    brokerHandler.send(msg1);
                }/*// TODO: No need of balancer letting the server know about server adding
                else if (msg.getMessageType() == SERVER_JOINED) {
                    //TODO: Different message type for server and balancer
                    Message msg1 = (Message) msg;

                }*/ else if (msg.getMessageType() == SERVER_EXITED) {
                    //TODO: Different message type for server and balancer
                    ServerCache serverCache = ServerCache.getInstance();
                    //TODO: remove ip from cache
                }else {
                    //msg.getMessageType() = GET_FRIENDS_REQUEST
                    FriendMessage msg1 = (FriendMessage) msg;
                    //TODO: Get friends from db
                    ArrayList<String> frnds = db.getFriends(msg1.getFromUser().getUserName());
                    for (String f : frnds) {
                        msg1.addFriend(new User(f));
                    }
                    brokerHandler.send(msg1);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}