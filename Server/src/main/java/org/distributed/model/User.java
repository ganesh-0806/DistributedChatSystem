package org.distributed.model;

import java.io.Serializable;

public class User implements Serializable {
    private String userName;
    private Integer userId;
    private static final long serialVersionUID = 2L;

    public User(String uname, Integer id) {
        userName = uname;
        userId = id;
    }

    public User(String uname) {
        userName = uname;
    }

    public User(){
    }

    public void setUserName(String name) {
        userName = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUid(Integer id) {
        userId = id;
    }

    public Integer getUid() {
        return userId;
    }
}