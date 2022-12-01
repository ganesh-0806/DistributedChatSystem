package org.distributed.connector;

import org.distributed.loadBalancer.LoadBalancer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BrokerManager extends Thread{
    private int port;
    private ServerSocket serverSocket;


    public BrokerManager()
    {
        try {
            serverSocket=new ServerSocket(8081);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                LoadBalancer loadBalancer=new LoadBalancer(socket);
                loadBalancer.start();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
          //  new ServerHandler(socket).start();
        }
    }
}
