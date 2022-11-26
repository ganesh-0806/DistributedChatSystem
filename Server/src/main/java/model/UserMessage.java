package model;

public class UserMessage extends Message{
    private String password;
    private String desc;

    private User user;

    UserMessage(User user, String password, String desc, MessageType type) {
        super(user, type);
        this.password = password;
        this.desc = desc;
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public String getDescription() {
        return this.desc;
    }

    public User getUser() {
        return this.user;
    }
}
