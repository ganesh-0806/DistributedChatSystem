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
        ObjectInputStream inp = null;
        BufferedReader brinp = null;
        Message msg;
        try {
            inp = (ObjectInputStream) socket.getInputStream();
            // TODO: verify if this gives messgase class
            msg = (Message) inp.readObject();
        } catch (IOException e) {
            return;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            if (msg == null) {
                socket.close();
                return;
            }
            else {
                ClientCache clientCache = ClientCache.getInstance();

                if(msg.getMessageType() == MessageType.TEXT_MESSAGE) {
                    User to = ((ChatMessage)msg).getToUser();
                    ClientHandler clientHandler = clientCache.getClient(to.getUserName());
                    if(clientHandler == null) {
                        clientCache.addClient(to.getUserName(), new ClientHandler(to));
                        clientHandler = clientCache.getClient(to.getUserName());
                    }
                    clientHandler.addMessage((ChatMessage) msg);
                }
                else {
                    ClientHandler clientHandler = clientCache.getClient(msg.getFromUser().getUserName());

                    if(msg.getMessageType() == MessageType.USER_LOGIN_FAIL || msg.getMessageType() == MessageType.USER_LOGIN_SUCCESSFUL
                        || msg.getMessageType() == MessageType.USER_LOGOUT_FAIL || msg.getMessageType() == MessageType.USER_LOGOUT_SUCCESSFUL) {
                        clientHandler.sendAuthResponse((UserMessage) msg);
                    }
                    else {
                        clientHandler.sendFriendResponse((FriendMessage) msg);
                    }
                }
            }
        }
        catch (IOException e){

        }
    }
}
