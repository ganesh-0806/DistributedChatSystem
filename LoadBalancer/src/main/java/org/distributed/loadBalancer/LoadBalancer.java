package org.distributed.loadBalancer;

import org.distributed.connector.*;
import org.distributed.model.*;
import java.io.IOException;
import java.io.*;
import java.net.ServerSocket;
import java.util.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class LoadBalancer extends Thread {

    //contains the main method of load balancer.
    // Spawns new threads - one to listen  to heartbeats from servers
    // one to start LoadBalancer Listener
    // other to start LoadBalancerSocket
    ObjectInputStream inputStream;
    Socket socket;

    public LoadBalancer(Socket socket) {
        this.socket = socket;
    }


    public void loadProcessing() {

        System.out.println("In loadBalancer's loadProcessing method");
        String clientUserName;
        try {

            System.out.println("Reading message");
            Message defaultMessage = (Message) inputStream.readObject();
            System.out.println("Message type:"+defaultMessage.getFromUser().getUserName());

            if(defaultMessage==null)
            {
                socket.close();
            }

            clientUserName = defaultMessage.getFromUser().getUserName();

            //sending messages from loadBalancer to any specific server;
            int serverChosen=0;
            HashMap<Integer, ServerHandler> mapping = ServerCache.getInstance().getServerMapping();
            if(clientUserName==null)
            {
                Random randomServerMap=new Random();
                serverChosen=randomServerMap.nextInt(mapping.size());
            }
            else {
                serverChosen = generateHash(clientUserName);
            }
            ServerHandler destinationServer = mapping.get(serverChosen);

            destinationServer.send(defaultMessage);

            System.out.println("Message forwarded to server-" + serverChosen);

        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void run() {
        System.out.println("Load Balancer thread is up and running");
        try {
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while(true) {
            loadProcessing();
        }
    }

    public int generateHash(String clientName) {

        HashMap<Integer, ServerHandler> serverMapping = ServerCache.getInstance().getServerMapping();
        int sum = 0;
        for (int i = 0; i < clientName.length(); i++) {
            sum += (int) clientName.charAt(i);
        }
        return sum % serverMapping.size();
    }
}
