package model;

import java.util.ArrayList;

public class FriendMessage extends Message{
    private ArrayList<User> friends;
    private String errDesc;

    public FriendMessage(User from, MessageType type){
        super(from, type);
        friends = new ArrayList<User>();
    }

    public FriendMessage(User from, String desc, MessageType type){
        super(from, type);
        this.errDesc = desc;
        friends = new ArrayList<User>();
    }

    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }

    public String getErrDesc() {
        return this.errDesc;
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
