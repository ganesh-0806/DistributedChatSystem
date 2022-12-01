package org.distributed;

import org.distributed.connector.BrokerManager;
import org.distributed.connector.ServerManager;

public class Main {
    public static void main(String[] args) {
        ServerManager serverManager=new ServerManager();
        serverManager.start();
        System.out.println("ServerManager started");
        BrokerManager brokerManager=new BrokerManager();
        brokerManager.start();
        System.out.println("BrokerManager started");


    }
}