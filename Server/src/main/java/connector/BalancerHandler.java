package connector;

import model.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import static model.MessageType.*;

public class BalancerHandler implements Runnable {
    private Socket socket;
    private Message msg;
    //private Message msg1;
    private InputStream inputStream;
    private BufferedReader bufferedInput;
    private ObjectOutputStream outputStream;

    private int port;

    public BalancerHandler(int port) {
        InetAddress host;
        this.port = port;
        try {
            host = InetAddress.getLocalHost();
            socket = new Socket(host, port);
            inputStream = socket.getInputStream();
            outputStream = (ObjectOutputStream) socket.getOutputStream();
            //outputStream = (ObjectOutputStream) socket.getOutputStream();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (true) {
            //receive input
            bufferedInput = new BufferedReader(new InputStreamReader(inputStream));
            //TODO: typecast read input into message
            msg = new Message();
            if (msg.getMessageType() == TEXT_MESSAGE) {
                ChatMessage msg1 = (ChatMessage) msg;
            } else if (msg.getMessageType() == USER_LOGOUT) {
                UserMessage msg1 = (UserMessage) msg;
            } else if (msg.getMessageType() == ADD_USER) {
                UserMessage msg1 = (UserMessage) msg;
                //1. check if user exists in db
                //if no, add user and return USER_LOGIN_SUCCESSFUL
                //else, return USER_LOGIN_FAIL
                if (!db.contains(msg1.getUser().getUserName())) {
                    new User(msg1.getUser().getUserName());
                    msg1.setMessageType(USER_LOGIN_SUCCESSFUL);
                } else {
                    msg1.setMessageType(USER_LOGIN_FAIL);
                }
            } else if (msg.getMessageType() == ADD_FRIEND_REQUEST) {
                FriendMessage msg1 = (FriendMessage) msg;
                UserMessage msg2 = (UserMessage) msg;
                //1. check if user has the friend added already
                //if no, add user to friend list and return ADD_FRIEND_SUCCESSFUL msg
                //else, return ADD_FRIEND_FAIL msg
                if (!db.add(msg2.getUser().getUserName())) {
                    msg1.addFriend(msg2.getUser());
                    msg1.setMessageType(ADD_FRIEND_SUCCESSFUL);
                } else {
                    msg1.setMessageType(ADD_FRIEND_FAIL);
                }
            } else if (msg.getMessageType() == SERVER_JOINED) {
                Message msg1 = (Message) msg;
            } else if (msg.getMessageType() == SERVER_EXITED) {
                Message msg1 = (Message) msg;
            } else {
                //msg.getMessageType() = GET_FRIENDS_REQUEST
                FriendMessage msg1 = (FriendMessage) msg;
                ArrayList<User> friendsList = msg1.getFriends();
                for (User f : friendsList) {
                    msg1.addFriend(f);
                }
            }
            try {
                BrokerHandler brokerHandler = BrokerHandler.getInstance();
                brokerHandler.send(msg1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
                // new thread to keep listening for input
                // new Thread(new BalancerHandler(port)).start();
            }
        }
    }
}