package org.distributed.model;

import java.io.Serializable;

public class ServerMessage extends Message implements Serializable {
    private String messageContent;

    private static final long serialVersionUID = 6L;

    public ServerMessage(String messageContent, MessageType messageType) {
        super(new User(""), messageType);
        this.messageContent = messageContent;
    }

    public void setMessageContent(String messageContent)
    {
        this.messageContent=messageContent;
    }
    public String getMessageContent()
    {
        return messageContent;
    }
}
