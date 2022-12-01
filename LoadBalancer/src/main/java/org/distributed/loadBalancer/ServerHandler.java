package org.distributed.loadBalancer;

import org.distributed.model.Message;
import org.distributed.model.ServerMessage;

import java.io.*;
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
    ObjectOutputStream oos;

    public ServerHandler(Socket socket)
    {
        this.socket=socket;
        try {
            oos = new ObjectOutputStream(this.socket.getOutputStream());
        }
        catch(Exception e) {
            System.out.println(e);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public ServerHandler(String socketAddress)
    {
        this.socketAddress=socketAddress;
    }

    public ObjectOutputStream getObjectOutputStream() {
        return oos;
    }

    void send(Message message) {
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {

        try {
            ois = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while(true) {

            readMessage(socket);
        }
    }

    private void readMessage(Socket socket) {
        try {

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
