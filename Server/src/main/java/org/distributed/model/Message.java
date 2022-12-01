package org.distributed.model;

import java.io.Serializable;

public class Message implements Serializable {
    private User fromUser;
    public MessageType type;
    private static final long serialVersionUID = 1L;

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
