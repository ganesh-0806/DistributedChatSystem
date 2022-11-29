package org.distributed.model;

public class ChatMessage extends Message{
    private User toUser;
    private String message;

    ChatMessage(User from, User to, String msg, MessageType type) {
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
