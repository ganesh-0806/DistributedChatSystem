package connector;

import model.ChatMessage;
import model.FriendMessage;
import model.Message;
import model.UserMessage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class BrokerHandler {
    private Socket socket;
    private static OutputStream brokerOutputStream;
    private static BrokerHandler brokerInstance = null;

    static BrokerHandler getInstance() throws IOException {
        if(brokerInstance == null)
        {
            brokerInstance = new BrokerHandler();
        }
        return brokerInstance;
    }

    BrokerHandler() throws IOException {
        try{
            InetAddress host = InetAddress.getLocalHost();
            socket = new Socket(host, 7777);
            brokerOutputStream = socket.getOutputStream();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(ChatMessage message)
    {
        brokerOutputStream.write(message);
    }

    public void send(UserMessage message)
    {
        brokerOutputStream.write(message);
    }

    public void send(FriendMessage message)
    {
        brokerOutputStream.write(message);
    }

}
