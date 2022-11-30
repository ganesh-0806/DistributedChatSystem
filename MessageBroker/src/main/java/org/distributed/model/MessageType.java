package org.distributed.model;

import com.fasterxml.jackson.annotation.*;

//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
//@JsonIgnoreProperties(ignoreUnknown = true)
public enum MessageType {

    // User Message
    //@JsonProperty("ADD_USER")
    ADD_USER,
    //@JsonProperty("USER_LOGIN_SUCCESSFUL")
    USER_LOGIN_SUCCESSFUL,//("USER_LOGIN_SUCCESSFUL"),
    //@JsonProperty("USER_LOGIN_FAIL")
    USER_LOGIN_FAIL,//("USER_LOGIN_FAIL"),
    //@JsonProperty("USER_LOGOUT")
    USER_LOGOUT,//("USER_LOGOUT"),
    //@JsonProperty("USER_LOGOUT_SUCCESSFUL")
    USER_LOGOUT_SUCCESSFUL,//("USER_LOGOUT_SUCCESSFUL"),
    //@JsonProperty("USER_LOGOUT_FAIL")
    USER_LOGOUT_FAIL, //("USER_LOGOUT_FAIL"),
    // Friend Message
    //@JsonProperty("ADD_FRIEND_REQUEST")
    ADD_FRIEND_REQUEST,//("ADD_FRIEND_REQUEST"),
    //@JsonProperty("ADD_FRIEND_SUCCESSFUL")
    ADD_FRIEND_SUCCESSFUL,//("ADD_FRIEND_SUCCESSFUL"),
    //@JsonProperty("ADD_FRIEND_FAIL")
    ADD_FRIEND_FAIL,//("ADD_FRIEND_FAIL"),
    //@JsonProperty("GET_FRIENDS_REQUEST")
    GET_FRIENDS_REQUEST,//("GET_FRIENDS_REQUEST"),
    //@JsonProperty("GET_FRIENDS_SUCCESSFUL")
    GET_FRIENDS_SUCCESSFUL,//("GET_FRIENDS_SUCCESSFUL"),
    //@JsonProperty("GET_FRIENDS_FAIL")
    GET_FRIENDS_FAIL,//("GET_FRIENDS_FAIL"),
    // Chat Message
    //@JsonProperty("TEXT_MESSAGE")
    TEXT_MESSAGE,//("TEXT_MESSAGE");

    /*private String type;
    MessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    /*
    @JsonCreator
    public static MessageType fromString(String type){
        return MessageType.valueOf(type);
    }
*/

}