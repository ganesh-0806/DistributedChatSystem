package org.distributed.connector;

import org.distributed.model.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class BrokerHandler {
    private Socket socket;
    private ObjectOutputStream brokerOutputStream;
    private static BrokerHandler brokerInstance = null;

    static BrokerHandler getInstance() throws IOException {
        if(brokerInstance == null)
        {
            brokerInstance = new BrokerHandler();
        }
        return brokerInstance;
    }

    BrokerHandler() {
        try{
            InetAddress host = InetAddress.getLocalHost();//100.26.218.146
            socket = new Socket("54.85.133.117", 8888);
            brokerOutputStream = new ObjectOutputStream (socket.getOutputStream());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(Message message)
    {
        System.out.println("semding message " + message.getFromUser().getUserName());
        try {
            brokerOutputStream.writeObject(message);
            brokerOutputStream.flush();
        }
        catch (IOException e) {

        }
    }

}
