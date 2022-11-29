package org.distributed;

import org.distributed.connector.BalancerHandler;
import org.distributed.fd.PingAck;

public class Main {
    public static void main(String[] args) {
        // Start Server Manager
        BalancerHandler balancerHandler = new BalancerHandler(8888);
        new Thread(balancerHandler).start();

        // Start ping acknowledger
        PingAck pingAck = new PingAck(balancerHandler);
        new Thread(pingAck).start();
    }
}