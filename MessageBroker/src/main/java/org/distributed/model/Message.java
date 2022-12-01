package org.distributed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;

@JsonTypeInfo(use= JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({@JsonSubTypes.Type(UserMessage.class), @JsonSubTypes.Type(ChatMessage.class), @JsonSubTypes.Type(FriendMessage.class)})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message implements Serializable {
    private User fromUser;
    private MessageType type;
    private static final long serialVersionUID = 1L;

    public Message(User user, MessageType type){
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

    public void setType(MessageType type) {
        this.type = type;
    }

    public MessageType getType() {
        return this.type;
    }

}
