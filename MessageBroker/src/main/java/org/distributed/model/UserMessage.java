package org.distributed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMessage extends Message {
    private String password;
    private String desc;
    private static final long serialVersionUID = 5L;

    public UserMessage(User user, String password, String desc, MessageType type) {
        super(user, type);
        this.password = password;
        this.desc = desc;
    }

    //LOGIN, FAILURES
    public UserMessage(User user, String password, MessageType type) {
        super(user, type);
        if(type == MessageType.ADD_USER) {
            this.password = password;
        }
        else {
            this.desc = password;
        }
    }

    public UserMessage() {
        super();
    }

    // LOGOUT, SUCCESSES
    public UserMessage(User user, MessageType type) {
        super(user, type);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }
}
