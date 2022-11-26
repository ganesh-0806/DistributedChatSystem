package java;

import connector.BalancerHandler;

public class Main {
    public static void main(String[] args) {
        // Start Server Manager
        BalancerHandler balancerHandler = new BalancerHandler(8888);
        new Thread(balancerHandler).start();
    }
}