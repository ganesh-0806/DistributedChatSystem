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
    String serverAddress;
    ServerMessage message;
    ServerSocket serverSocket;
    Socket socket;
    String senderSocketAddress;
    String socketAddress;
    ObjectInputStream ois;
    LoadBalancer loadBalancer;
    /*
    public ServerHandler(int serverPort) {
        this.serverPort = serverPort;
    }*/

    public ServerHandler(Socket socket)
    {
        this.socket=socket;
        loadBalancer = new LoadBalancer();
    }
    /*
    public ServerHandler(ServerMessage msg, String senderSocketAddress) {
        this.message = msg;
        this.senderSocketAddress = senderSocketAddress;
    }
*/
    public ServerHandler(String socketAddress)
    {
        this.socketAddress=socketAddress;
    }

    public void run() {
        while(true) {

            readMessage(socket);
           // loadBalancer.heartBeatListener(message, senderSocketAddress);
        }
    }

    private void readMessage(Socket socket) {
        try {
            ois=new ObjectInputStream(socket.getInputStream());
            ServerMessage serverMessage=(ServerMessage) ois.readObject();
            loadBalancer.heartBeatListener(serverMessage, socket.getInetAddress().getHostAddress());
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
class ServerHelper extends Thread
{
    long oldTime= TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    long newTime;
    public void run()
    {
        while(true)
        {
            newTime= TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
            if(newTime-oldTime>30)
            {
                LoadBalancer.reFreshServerStatus();
                oldTime=newTime;
            }
        }
    }
}
