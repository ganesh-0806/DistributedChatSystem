package org.example.loadBalancer;

import org.example.connector.*;
import org.example.model.*;
import java.io.IOException;
import java.io.*;
import java.net.ServerSocket;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.net.Socket;
public class LoadBalancer extends Thread {

    //contains the main method of load balancer.
    // Spawns new threads - one to listen  to heartbeats from servers
    // one to start LoadBalancer Listener
    // other to start LoadBalancerSocket
    HashMap<ServerManager, Date> heartbeats;
    int hashPrime=7;
    HashMap<Integer, ServerHandler> serverMapping;
    LoadBalancer loadBalancer;
    //ArrayList<LoadBalancer> peers;
    ServerSocket serverSocket;
    Socket loadBalancerSocket;
    String loadBalancerAddress;
    int loadBalancerPort;
    public LoadBalancer()
    {
        this("10.0.0.181",8081);
    }
    LoadBalancer(int port)
    {
        this.loadBalancerPort=port;
    }
    
    LoadBalancer(String address,int port)
    {
        this.loadBalancerAddress=address;
        this.loadBalancerPort=port;
    }

    public LoadBalancer getLoadBalancer() {
        return loadBalancer;
    }

    public void setLoadBalancer(int port)
    {
        loadBalancer=new LoadBalancer(this.loadBalancerAddress, port);
    }

    public void setLoadBalancerAddress(String IP)
    {
        this.loadBalancerAddress=IP;
    }

    public String getLoadBalancerAddress()
    {
        return loadBalancerAddress;
    }

    public void setLoadBalancerPort(int port)
    {
        this.loadBalancerPort = port;
    }

    public int getLoadBalancerPort()
    {
        return loadBalancerPort;
    }

    public void initiateServerMapping()
    {
        //TO-DO
        serverMapping=new HashMap<Integer, ServerHandler>();
    }

    public void extendServerMapping(ServerHandler s, Integer n)
    {
        //TO-DO: add new server to the mapping
        serverMapping.put(n,s);
    }

    public void loadBalancerStartUp()
    {
        try {
            if (loadBalancerAddress != null && loadBalancerPort != 0) {
                System.out.println("InetAddress is : "+InetAddress.getLocalHost().getHostAddress());
                serverSocket = new ServerSocket(loadBalancerPort);
                while (true) {
                    System.out.println("Starting load balancer");
                    try {
                        loadBalancerSocket = serverSocket.accept();
                        System.out.println("Received message at loadBalancer");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    loadProcessing(loadBalancerSocket);
                }
            } else {
                System.out.println("Load Balancer could not be instantiated. Retry ");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    public void loadProcessing(Socket ss)
    {
        DataInputStream in = null;
        try {
            in = new DataInputStream(ss.getInputStream());
        ObjectInputStream input = new ObjectInputStream(in);

        Message message;
        try {
            Message defaultMessage = (Message) input.readObject();
            if(defaultMessage.type==MessageType.TEXT_MESSAGE) {
                message = (ChatMessage) input.readObject();
            }
            else
            {
                message=(UserMessage)input.readObject();
            }
            String clientUserName= message.getFromUser().getUserName();

            System.out.println(clientUserName);

            Integer serverChosen=generateHash(clientUserName);
            ServerHandler destinationServer=serverMapping.get(serverChosen);
            Socket destination=new Socket(destinationServer.getServerAddress(), destinationServer.getServerPort());
            DataOutputStream out = new DataOutputStream(destination.getOutputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);

            objectOutputStream.writeObject(input);
            System.out.println("Message forwarded to server-"+serverChosen);

        } catch (ClassNotFoundException ex) {

        }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run()
    {
        // HTTPConnectionWrapper socket=new HTTPConnectionWrapper(8080);
        loadBalancerStartUp();
    }
    public static void main(String args[]) {
        LoadBalancer loadBalancer = null;
        try {
            loadBalancer = new LoadBalancer(InetAddress.getLocalHost().getHostAddress(), 8081);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        // HeartBeat heartBeatListener=new HeartBeat();

        loadBalancer.start();
        //heartBeatListener.start();
        /*LoadBalancerListener listener = new LoadBalancerListener();
        listener.start();
        LoadBalancerSocket socket = new LoadBalancerSocket();
        socket.start();*/
    }
    public int generateHash(String clientName)
    {
        int sum=0;
        for(int i=0;i<clientName.length();i++)
        {
            sum+=(int)clientName.charAt(i);
        }
        return sum%serverMapping.size();
    }
}
