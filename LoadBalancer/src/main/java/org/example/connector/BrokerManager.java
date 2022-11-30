package org.example.connector;

import org.example.loadBalancer.LoadBalancer;
import org.example.loadBalancer.ServerHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BrokerManager extends Thread{
    private int port;
    private ServerSocket serverSocket;


    BrokerManager()
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
