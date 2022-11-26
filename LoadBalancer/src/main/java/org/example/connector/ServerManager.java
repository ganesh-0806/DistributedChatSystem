package org.example.connector;


import org.example.loadBalancer.LoadBalancer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerManager implements Runnable{
    private int port;
    private ServerSocket serverSocket;
    private Socket socket;

    //@Override
    public void run() {
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("I/O error: " + e);
            }
            // new thread for a client
            //new Thread(new ServerHandler(socket)).start();
        }
    }
}
