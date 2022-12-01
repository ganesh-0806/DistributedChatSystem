package org.distributed.model;

import java.io.Serializable;

public enum MessageType implements Serializable {
    ADD_USER, TEXT_MESSAGE, USER_LOGIN_FAIL, USER_LOGIN_SUCCESSFUL, USER_LOGOUT, USER_LOGOUT_FAIL, USER_LOGOUT_SUCCESSFUL, SERVER_JOINED, SERVER_EXITED

}