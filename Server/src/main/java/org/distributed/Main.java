package org.distributed;

import org.distributed.connector.BalancerHandler;
import org.distributed.fd.PingAck;

public class Main {
    public static void main(String[] args) {
        // Start Server Manager
        BalancerHandler balancerHandler = new BalancerHandler(9091);
        new Thread(balancerHandler).start();
        System.out.println("Balancer thread started");

        // Start ping acknowledger
        PingAck pingAck = new PingAck(balancerHandler);
        new Thread(pingAck).start();
    }
}