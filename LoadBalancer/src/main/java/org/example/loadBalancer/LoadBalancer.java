package org.example.loadBalancer;

import org.example.connector.*;
import org.example.model.*;
import java.io.IOException;
import java.io.*;
import java.net.ServerSocket;
import java.net.*;
import java.util.*;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class LoadBalancer extends Thread {

    //contains the main method of load balancer.
    // Spawns new threads - one to listen  to heartbeats from servers
    // one to start LoadBalancer Listener
    // other to start LoadBalancerSocket
    HashMap<BrokerManager, Date> heartbeats;
    int hashPrime = 7;
    static int id=0;
    HashMap<Integer, ServerHandler> serverMapping;
    HashMap<ServerHandler, Socket> serverSockets;
    static HashMap<String, Integer> serverStatus;
    LoadBalancer loadBalancer;
    //ArrayList<LoadBalancer> peers;
    ServerSocket serverSocket;
    Socket loadBalancerSocket;
    String loadBalancerAddress;
    int loadBalancerPort;
    DataInputStream inputStream;
    Socket socket;

    public LoadBalancer(Socket socket) {
        this.socket = socket;
        try {
            this.inputStream = (DataInputStream) socket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //this("10.0.0.181", 8081);
    }

    LoadBalancer(int port) {
        this.loadBalancerPort = port;
    }

    public LoadBalancer()
    {
        this("localhost", 8081);
    }
    public LoadBalancer(String address, int port) {
        this.loadBalancerAddress = address;
        this.loadBalancerPort = port;
        try {
            socket=new Socket(address, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void reFreshServerStatus() {
        for(Map.Entry<String, Integer> server : serverStatus.entrySet()) {
            String key = server.getKey();
            serverStatus.put(key,0);
        }
    }

    public LoadBalancer getLoadBalancer() {
        return loadBalancer;
    }

    public void initiateServerStatus()
    {
        serverStatus=new HashMap();
        for(Map.Entry<Integer, ServerHandler> server : serverMapping.entrySet()) {
            String key = server.getValue().socketAddress;
            serverStatus.put(key,0);
        }
        ServerHelper serverHelper=new ServerHelper();
        serverHelper.start();
    }
    public void setLoadBalancer(int port) {
        loadBalancer = new LoadBalancer(this.loadBalancerAddress, port);
    }

    public void setLoadBalancerAddress(String IP) {
        this.loadBalancerAddress = IP;
    }

    public String getLoadBalancerAddress() {
        return loadBalancerAddress;
    }

    public void setLoadBalancerPort(int port) {
        this.loadBalancerPort = port;
    }

    public int getLoadBalancerPort() {
        return loadBalancerPort;
    }

    public void initiateServerMapping() {
        //TO-DO
        serverMapping = new HashMap<Integer, ServerHandler>();
    }

    public void extendServerMapping(ServerHandler s, Integer n) {
        //TO-DO: add new server to the mapping
        serverMapping.put(id++, s);
        try {
            Socket mappedSocket = new Socket(s.getServerAddress(), s.getServerPort());
            serverSockets.put(s, mappedSocket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadProcessing(Socket socket) {
        DataInputStream in = null;
        System.out.println("In loadBalancer's loadProcessing method");
        try {
            in = new DataInputStream(socket.getInputStream());
            ObjectInputStream input = new ObjectInputStream(in);
            Message message;
            String clientUserName;
            try {
                Message defaultMessage = (Message) input.readObject();
                if(defaultMessage==null)
                {
                    socket.close();
                }
                if (defaultMessage.type == MessageType.TEXT_MESSAGE) {
                    message = (ChatMessage) input.readObject();
                    clientUserName = ((ChatMessage) message).getToUser().getUserName();
                } else {
                    message = (UserMessage) input.readObject();
                    clientUserName = null;
                }

                System.out.println("client is:"+ clientUserName);
                System.out.println("Message type:"+defaultMessage.type);


                //sending messages from loadBalancer to any specific server;
                int serverChosen=0;
                if(clientUserName==null)
                {
                    Random randomServerMap=new Random();
                    serverChosen=randomServerMap.nextInt(serverMapping.size());
                }
                else {
                    serverChosen = generateHash(clientUserName);
                }
                ServerHandler destinationServer = serverMapping.get(serverChosen);
                Socket destination=serverSockets.get(destinationServer);

                DataOutputStream out = new DataOutputStream(destination.getOutputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);

                objectOutputStream.writeObject(input);
                System.out.println("Message forwarded to server-" + serverChosen);

            } catch (ClassNotFoundException ex) {

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        // HTTPConnectionWrapper socket=new HTTPConnectionWrapper(8080);
        System.out.println("Load Balancer thread is up and running");
        while(true) {
            loadProcessing(this.socket);
        }
    }
/*
    public static void main(String args[]) {
        LoadBalancer loadBalancer = null;

        try {
            System.out.println(InetAddress.getLocalHost());
            loadBalancer = new LoadBalancer(InetAddress.getLocalHost().getHostAddress(), 8081);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        loadBalancer.start();
    }
*/
    public int generateHash(String clientName) {
        int sum = 0;
        for (int i = 0; i < clientName.length(); i++) {
            sum += (int) clientName.charAt(i);
        }
        return sum % serverMapping.size();
    }

    public void heartBeatListener(ServerMessage message, String senderSocketAddress) {
        if(serverMapping.containsValue(senderSocketAddress))
        {
            if(message.getMessageType().equals(MessageType.SERVER_JOINED))
            {
                extendServerMapping(new ServerHandler(message.getMessageContent()), id++);
            }
            else if(message.getMessageType().equals(MessageType.SERVER_EXITED))
            {
                long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
                int statusSoFar=serverStatus.get(message.getMessageContent());
                if(statusSoFar==serverMapping.size()-1) {
                    shrinkServerMapping(message.getMessageContent());
                }
                else
                    serverStatus.put(message.getMessageContent(),serverStatus.get(message.getMessageContent())+1);
            }
        }
    }

    private void shrinkServerMapping(String serverIPAddress) {
        int exitedNodeID=-1;
        ServerHandler exited=null;
        if(serverMapping.containsValue(serverIPAddress))
        {
            for (Map.Entry<Integer, ServerHandler> entry : serverMapping.entrySet()) {
                if (Objects.equals(entry.getValue().getServerAddress(), serverIPAddress)) {
                    exited=entry.getValue();
                    exitedNodeID = entry.getKey();
                }
            }
            if(exitedNodeID!=-1)
                serverMapping.remove(exitedNodeID);
                serverSockets.remove(exited);
        }
    }
}
