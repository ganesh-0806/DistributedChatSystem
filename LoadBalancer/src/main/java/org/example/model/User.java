package org.example.model;

public class User {
    private String userName;
    private Integer userId;

    public User(String uname, Integer id) {
        userName = uname;
        userId = id;
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