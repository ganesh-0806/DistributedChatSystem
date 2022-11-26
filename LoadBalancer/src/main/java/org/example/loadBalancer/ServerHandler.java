package org.example.loadBalancer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class ServerHandler {
    int serverPort;
    String serverAddress;
    ServerSocket serverSocket;
    public ServerHandler(int port) {
        this.serverPort = port;
        try {
            serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
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
