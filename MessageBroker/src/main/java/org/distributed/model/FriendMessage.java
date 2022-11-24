package org.distributed.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendMessage extends Message implements Serializable {
    private ArrayList<User> friends;
    private String desc;

    public FriendMessage(User from, MessageType type){
        super(from, type);
        friends = new ArrayList<User>();
    }

    public FriendMessage(User from, String desc, MessageType type){
        super(from, type);
        this.desc = desc;
        friends = new ArrayList<User>();
    }

    public FriendMessage(){
        super();
    }

    public void setDesc(String errDesc) {
        this.desc = errDesc;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public ArrayList<User> getFriends() {
        return this.friends;
    }

    public void addFriend(User friend) {
        this.friends.add(friend);
    }

    public void removeFriend(User friend) {
        this.friends.remove(friend);
    }
}
