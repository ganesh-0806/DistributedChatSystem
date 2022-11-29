package org.distributed;

import org.distributed.connector.BalancerHandler;

public class Main {
    public static void main(String[] args) {
        // Start Server Manager
        BalancerHandler balancerHandler = new BalancerHandler(8888);
        new Thread(balancerHandler).start();
    }
}