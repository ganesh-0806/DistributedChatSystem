package org.example.connector;

import org.example.loadBalancer.LoadBalancer;
import org.example.loadBalancer.ServerHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class ServerManager extends Thread {

    private ServerSocket loadServerSocket;
    Socket loadSocket;
    LoadBalancer loadBalancer=new LoadBalancer();


    public void run()
    {
        try {
            loadServerSocket=new ServerSocket(loadBalancer.getLoadBalancerPort());
            loadSocket= loadServerSocket.accept();
            new ServerHandler(loadSocket).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String args[])
    {
        ServerManager serverManager=new ServerManager();
        serverManager.start();
    }

}
