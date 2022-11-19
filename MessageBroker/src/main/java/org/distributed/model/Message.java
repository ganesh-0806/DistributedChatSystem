package org.distributed.model;

public abstract class Message {
    private User fromUser;
    private MessageType type;

    Message(User user, MessageType type){
        this.fromUser = user;
        this.type = type;
    }

    Message() {

    }

    public void setFromUser(User user) {
        this.fromUser = user;
    }

    public User getFromUser() {
        return this.fromUser;
    }

    public void setMessageType(MessageType type) {
        this.type = type;
    }

    public MessageType getMessageType() {
        return this.type;
    }
}
