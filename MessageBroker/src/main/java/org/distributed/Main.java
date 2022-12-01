package org.distributed;

import org.distributed.connector.ClientManager;
import org.distributed.connector.ServerManager;

public class Main {
    public static void main(String[] args) {
        // Start Server Manager
        //ServerManager serverManager = new ServerManager(8888);
	ServerManager serverManager = new ServerManager((ArrayList<Integer>) Arrays.asList(8888, 8080));
        new Thread(serverManager).start();
        // Start Client Manager
        ClientManager clientManager = new ClientManager(5678);
        clientManager.start();

        System.out.println("Threads started");

        /*while(true){
        }*/
    }
}
