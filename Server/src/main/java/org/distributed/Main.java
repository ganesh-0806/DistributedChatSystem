package org.distributed;

import org.distributed.connector.BalancerHandler;
import org.distributed.db.MongoConnection;
import org.distributed.fd.PingAck;

public class Main {
    public static void main(String[] args) {

        MongoConnection.getInstance();
        // Start Server Manager
        BalancerHandler balancerHandler = new BalancerHandler(9099);
        new Thread(balancerHandler).start();
        System.out.println("Balancer thread started");

        // Start ping acknowledger
        PingAck pingAck = new PingAck(balancerHandler);
        new Thread(pingAck).start();
    }
}