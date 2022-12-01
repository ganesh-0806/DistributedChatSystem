package org.distributed.loadBalancer;

import org.distributed.model.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class ServerHandler extends Thread {
    int serverPort;
    Socket socket;
    String socketAddress;
    ObjectInputStream ois;

    public ServerHandler(Socket socket)
    {
        this.socket=socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public ServerHandler(String socketAddress)
    {
        this.socketAddress=socketAddress;
    }

    public void run() {
        while(true) {

            readMessage(socket);
        }
    }

    private void readMessage(Socket socket) {
        try {
            ois=new ObjectInputStream(socket.getInputStream());

            ServerMessage serverMessage=(ServerMessage) ois.readObject();
            ServerCache.getInstance().heartBeatListener(serverMessage, socket.getInetAddress().getHostAddress());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int getServerPort()
    {
        return this.serverPort;
    }

    public String getServerAddress() {
        try {
            return InetAddress.getLocalHost().toString();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
