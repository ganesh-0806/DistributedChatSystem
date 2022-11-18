package org.distributed.broker;

import org.distributed.model.ChatMessage;
import org.distributed.model.Message;
import org.distributed.model.MessageType;
import org.distributed.model.UserMessage;

import java.io.*;
import java.net.Socket;

public class ServerHandler implements Runnable{

    private Socket socket;
    public ServerHandler(Socket socket) {
        this.socket = socket;

    }

    @Override
    public void run() {
        InputStream inp = null;
        BufferedReader brinp = null;
        Message msg;
        try {
            inp = socket.getInputStream();
            brinp = new BufferedReader(new InputStreamReader(inp));
            //TODO: typecast read input into message
            msg = new UserMessage();
        } catch (IOException e) {
            return;
        }

        try {
            if (msg == null) {
                socket.close();
                return;
            }
            else {
                ClientCache clientCache = ClientCache.getInstance();
                ClientHandler clientHandler = clientCache.getClient(msg.getFromUser().getUserName());
                if(clientHandler == null) {
                    clientCache.addClient(msg.getFromUser().getUserName(), new ClientHandler(msg.getFromUser()));
                    clientHandler = clientCache.getClient(msg.getFromUser().getUserName());
                }

                if(msg.getMessageType() == MessageType.TEXT_MESSAGE) {
                    clientHandler.addMessage((ChatMessage) msg);
                }
                else {
                    clientHandler.sendAuthResponse((UserMessage) msg);
                }
            }
        }
        catch (IOException e){

        }
    }
}
