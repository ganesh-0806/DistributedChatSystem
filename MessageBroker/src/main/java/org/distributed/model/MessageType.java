package org.distributed.model;

import com.fasterxml.jackson.annotation.*;

import java.io.Serializable;

//@JsonFormat(shape = JsonFormat.Shape.OBJECT)
//@JsonIgnoreProperties(ignoreUnknown = true)
public enum MessageType {

    // User Message
    ADD_USER,
    USER_LOGIN_SUCCESSFUL,
    USER_LOGIN_FAIL,
    USER_LOGOUT,
    USER_LOGOUT_SUCCESSFUL,
    USER_LOGOUT_FAIL,
    // Friend Message
    ADD_FRIEND_REQUEST,
    ADD_FRIEND_SUCCESSFUL,
    ADD_FRIEND_FAIL,
    GET_FRIENDS_REQUEST,
    GET_FRIENDS_SUCCESSFUL,
    GET_FRIENDS_FAIL,
    // Chat Message
    TEXT_MESSAGE
}