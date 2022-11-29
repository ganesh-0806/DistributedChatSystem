package org.distributed.model;

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
    TEXT_MESSAGE,
    SERVER_JOINED,
    SERVER_EXITED
}