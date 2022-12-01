package org.distributed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatMessage extends Message {
    private User toUser;
    private String message;
    private static final long serialVersionUID = 4L;
    public ChatMessage(User from, User to, String msg, MessageType type) {
        super(from, type);
        toUser = to;
        message = msg;
    }

    public ChatMessage() {
        super();

    }

    public void setToUser(User to) {
        toUser = to;
    }

    public User getToUser() {
        return toUser;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
