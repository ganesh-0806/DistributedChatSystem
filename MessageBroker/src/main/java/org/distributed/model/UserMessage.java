package org.distributed.model;

public class UserMessage extends Message{
    private String password;
    private String errDesc;

    UserMessage(User user, String password, String desc, MessageType type) {
        super(user, type);
        this.password = password;
        this.errDesc = desc;
    }

    //LOGIN, FAILURES
    UserMessage(User user, String msg, MessageType type) {
        super(user, type);
        if(type == MessageType.ADD_USER) {
            this.password = msg;
        }
        else {
            this.errDesc = msg;
        }
    }

    // LOGOUT, SUCCESSES
    UserMessage(User user, MessageType type) {
        super(user, type);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setDescription(String desc) {
        this.errDesc = desc;
    }

    public String getDescription() {
        return this.errDesc;
    }
}
