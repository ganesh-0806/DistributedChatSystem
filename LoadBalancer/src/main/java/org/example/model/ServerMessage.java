package org.example.model;

public class ServerMessage {
    private MessageType messageType;
    private String messageContent;

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
    public void setMessageContent(String messageContent)
    {
        this.messageContent=messageContent;
    }
    public String getMessageContent()
    {
        return messageContent;
    }
    public MessageType getMessageType()
    {
        return messageType;
    }
}
