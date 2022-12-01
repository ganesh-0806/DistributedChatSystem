package org.distributed.broker;

import org.distributed.model.*;
import org.distributed.broker.ClientCache;

import java.io.*;
import java.net.Socket;

public class ServerHandler implements Runnable{

    private Socket socket;
    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inp = new ObjectInputStream(this.socket.getInputStream());

            Message msg;
            while(true) {
                // TODO: verify if this gives messgase class
                msg = (Message) inp.readObject();
                if (msg == null) {
                    socket.close();
                    return;
                }
                else {
                    ClientCache clientCache = ClientCache.getInstance();

                    if (msg.getType() == MessageType.TEXT_MESSAGE) {
                        User to = ((ChatMessage) msg).getToUser();
                        ClientHandler clientHandler = clientCache.getClient(to.getUserName());
                        if (clientHandler == null) {
                            clientCache.addClient(to.getUserName(), new ClientHandler(to));
                            clientHandler = clientCache.getClient(to.getUserName());
                        }
                        clientHandler.addMessage((ChatMessage) msg);
                    } else {
                        ClientHandler clientHandler = clientCache.getClient(msg.getFromUser().getUserName());

                        if (msg.getType() == MessageType.USER_LOGIN_FAIL || msg.getType() == MessageType.USER_LOGIN_SUCCESSFUL
                                || msg.getType() == MessageType.USER_LOGOUT_FAIL || msg.getType() == MessageType.USER_LOGOUT_SUCCESSFUL) {
                            clientHandler.sendAuthResponse((UserMessage) msg);
                        } else {
                            clientHandler.sendFriendResponse((FriendMessage) msg);
                        }
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }


    }
}
