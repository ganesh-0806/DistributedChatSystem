package org.distributed.connector;

import org.distributed.loadBalancer.ServerCache;
import org.distributed.loadBalancer.ServerHandler;

import java.io.IOException;
import java.net.*;

public class ServerManager extends Thread {

    private ServerSocket loadServerSocket;
    //LoadBalancer loadBalancer=new LoadBalancer();


    public void run()
    {
        try {
            loadServerSocket=new ServerSocket(9099);
            Socket loadSocket= loadServerSocket.accept();
            System.out.println("Server socket accepted");
            ServerHandler handler = new ServerHandler(loadSocket);
            System.out.println("Server handler created");
            ServerCache.getInstance().extendServerMapping(handler, 1);
            handler.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
