package org.distributed.fd;

import org.distributed.connector.BalancerHandler;
import org.distributed.connector.BrokerHandler;

import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PingAck implements Runnable{

    private BalancerHandler balancerHandler;
    private ServerCache serverCache;

    public PingAck(BalancerHandler balancerHandler) {
        this.balancerHandler = balancerHandler;
        this.serverCache = ServerCache.getInstance();
        Thread pingThread = new Thread(new Pinger());
        pingThread.start();
        Thread ackThread = new Thread(new Acknowledger());
        ackThread.start();
    }

    @Override
    public void run() {
        while(true) {
            Map<String, Long> ipTime = serverCache.getMap();

            for (Map.Entry<String, Long> entry : ipTime.entrySet()) {
                String key = entry.getKey();
                Long value = entry.getValue();

                long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

                // Time difference greater than 5 seconds send loader the fault detection
                if(timeSeconds - value > 5) {
                    //TODO: send SERVER_EXITED info to balancer

                }
            }
        }
    }

    public class Acknowledger implements Runnable {

        private DatagramSocket socket;

        Acknowledger() {

            try {
                socket = new DatagramSocket(4445);
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            while(true) {
                byte[] receiveData = new byte[8];
                DatagramPacket receivePacket = new DatagramPacket(receiveData,
                        receiveData.length);
                if(serverCache.isIpExists(receivePacket.getAddress().toString()) == false) {
                    //TODO: send server joined message to balancer
                }
                serverCache.addIp(receivePacket.getAddress().toString());
            }
        }
    }

    public class Pinger implements Runnable{

        private DatagramSocket socket;
        private String broadcastMessage = "Ping";
        public Pinger() {
            try {
                socket = new DatagramSocket();
                socket.setBroadcast(true);
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }
        }


        @Override
        public void run() {

            while(true) {

                byte[] buffer = broadcastMessage.getBytes();

                DatagramPacket packet = null;
                try {
                    packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("255.255.255.255"), 4445);
                    socket.send(packet);
                    Thread.sleep(2000);
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
}
