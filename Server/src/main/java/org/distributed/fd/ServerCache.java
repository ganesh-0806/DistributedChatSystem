package org.distributed.fd;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerCache {

    private static ServerCache instance = null;
    private Map<String, Long> ipToTime;
    private final Lock cacheLock = new ReentrantLock();

    public static ServerCache getInstance() {
        if(instance == null) {
            instance = new ServerCache();
        }

        return instance;
    }

    private ServerCache() {
        ipToTime = new HashMap<String, Long>();
    }

    public void addIp(String ip) {
        long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        cacheLock.lock();
        ipToTime.put(ip, timeSeconds);
        cacheLock.unlock();
    }

    public void removeIp(String ip) {
        cacheLock.lock();
        ipToTime.remove(ip);
        cacheLock.unlock();
    }

    public Map<String, Long> getMap() {
        return this.ipToTime;
    }

    public boolean isIpExists(String ip) {
        return this.ipToTime.containsKey(ip);
    }


}
