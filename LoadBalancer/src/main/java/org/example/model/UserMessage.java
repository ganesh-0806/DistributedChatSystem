package org.example.model;

public class UserMessage extends Message{
    private String password;
    private String desc;

    UserMessage(User user, String password, String desc, MessageType type) {
        super(user, type);
        this.password = password;
        this.desc = desc;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public String getDescription() {
        return this.desc;
    }
}
