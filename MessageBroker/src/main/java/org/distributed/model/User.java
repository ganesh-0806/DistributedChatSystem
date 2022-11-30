package org.distributed.model;

import java.util.Objects;

public class User {
    private String userName;
    private Integer userId;

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

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if(null == o || o.getClass() != this.getClass()) return false;

        User other = (User) o;

        return Objects.equals(this.userName, other.userName);
    }
}