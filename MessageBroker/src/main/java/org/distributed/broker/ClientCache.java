package org.distributed.broker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClientCache {
    private static ClientCache instance = null;
    private Map<String, ClientHandler> userClientCache;
    private Map<String, Thread> userClientThreads;
    private final Lock cacheLock = new ReentrantLock();

    public static ClientCache getInstance() {
        if(instance == null) {
            instance = new ClientCache();
        }

        return instance;
    }

    private ClientCache() {
        userClientCache = new HashMap<String, ClientHandler>();
        userClientThreads = new HashMap<String, Thread>();
    }

    public void addClient(String userName, ClientHandler client) {
        cacheLock.lock();
        try {
            Thread t = new Thread(client);
            t.start();
            userClientThreads.put(userName, t);
            userClientCache.put(userName, client);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            cacheLock.unlock();
        }
    }

    public ClientHandler getClient(String userName) {
        ClientHandler client = null;
        cacheLock.lock();
        try {
             client = userClientCache.get(userName);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        finally {
            cacheLock.unlock();
        }

        return client;
    }


}
