package org.distributed.loadBalancer;

import org.distributed.model.MessageType;
import org.distributed.model.ServerMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerCache {
    private static ServerCache instance = null;
    static int id=0;
    private HashMap<String, Integer> serverStatus;
    private HashMap<Integer, ServerHandler> serverMapping;
    private final Lock cacheLock = new ReentrantLock();

    public static ServerCache getInstance() {
        if(instance == null) {
            instance = new ServerCache();
        }

        return instance;
    }

    private ServerCache() {
        serverMapping = new HashMap<Integer, ServerHandler>();
        serverStatus = new HashMap<String, Integer>();
    }

    public HashMap<Integer, ServerHandler> getServerMapping() {
        return serverMapping;
    }

    public void extendServerMapping(ServerHandler s, Integer n) {
        //TO-DO: add new server to the mapping
        System.out.println("Server mapping about to be filled");
        serverMapping.put(id++, s);
        System.out.println("Server mapping is filled");
    }

    public void reFreshServerStatus() {
        for(Map.Entry<String, Integer> server : serverStatus.entrySet()) {
            String key = server.getKey();
            serverStatus.put(key,0);
        }
    }

    public void initiateServerStatus()
    {
        serverStatus=new HashMap();
        for(Map.Entry<Integer, ServerHandler> server : serverMapping.entrySet()) {
            String key = server.getValue().socketAddress;
            serverStatus.put(key,0);
        }
    }

    public void initiateServerMapping() {
        //TO-DO
        serverMapping = new HashMap<Integer, ServerHandler>();
    }
    private void shrinkServerMapping(String serverIPAddress) {
        int exitedNodeID=-1;
        ServerHandler exited=null;
        if(serverMapping.containsValue(serverIPAddress))
        {
            for (Map.Entry<Integer, ServerHandler> entry : serverMapping.entrySet()) {
                if (Objects.equals(entry.getValue().getServerAddress(), serverIPAddress)) {
                    exited=entry.getValue();
                    exitedNodeID = entry.getKey();
                }
            }
            if(exitedNodeID!=-1)
                serverMapping.remove(exitedNodeID);
        }
    }
    public void heartBeatListener(ServerMessage message, String senderSocketAddress) {
        if(serverMapping.containsValue(senderSocketAddress))
        {
            if(message.getMessageType().equals(MessageType.SERVER_JOINED))
            {
                extendServerMapping(new ServerHandler(message.getMessageContent()), id++);
            }
            else if(message.getMessageType().equals(MessageType.SERVER_EXITED))
            {
                long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
                int statusSoFar=serverStatus.get(message.getMessageContent());
                if(statusSoFar==serverMapping.size()-1) {
                    shrinkServerMapping(message.getMessageContent());
                }
                else
                    serverStatus.put(message.getMessageContent(),serverStatus.get(message.getMessageContent())+1);
            }
        }
    }

}
