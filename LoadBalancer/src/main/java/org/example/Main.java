package org.example;

import org.example.connector.BrokerManager;
import org.example.connector.ServerManager;
import org.example.loadBalancer.LoadBalancer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        ServerManager serverManager=new ServerManager();
        serverManager.start();
        System.out.println("ServerManager started");
        BrokerManager brokerManager=new BrokerManager();
        brokerManager.start();
        System.out.println("BrokerManager started");
        //LoadBalancer loadBalancer = null;
/*
        try {
            System.out.println(InetAddress.getLocalHost());
            loadBalancer = new LoadBalancer(InetAddress.getLocalHost().getHostAddress(), 8081);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }*/
        //loadBalancer.start();

/*
        try {

            while(true){
                System.out.println("Hello world!");
                Socket socket=new Socket("3.88.145.60",8081);
                System.out.println("Connected to local client");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }*/
        /*
        System.out.println("Hello world!");
        try {

            while(true){
                Socket socket=new Socket("3.88.145.60",8081);
                System.out.println("Connected to local client");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }*/


    }
}