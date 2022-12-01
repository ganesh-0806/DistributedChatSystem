package org.distributed.connector;

import org.distributed.loadBalancer.ServerCache;
import org.distributed.loadBalancer.ServerHandler;

import java.io.IOException;
import java.net.*;

public class ServerManager extends Thread {

    private ServerSocket loadServerSocket;
    Socket loadSocket;
    //LoadBalancer loadBalancer=new LoadBalancer();


    public void run()
    {
        try {
            loadServerSocket=new ServerSocket(9091);
            loadSocket= loadServerSocket.accept();
            ServerHandler handler = new ServerHandler(loadSocket);
            handler.start();
            ServerCache.getInstance().extendServerMapping(handler, 1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
