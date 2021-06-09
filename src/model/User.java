package model;

public class User extends Hasher {
    private String userName;
    private String userPassword;
    private long userId;

    public User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public static User createUser(String userName, String userPassword) {
        Hasher hasher = new Hasher();
        User user = new User(userName, userPassword);
        hasher.hash(userPassword);

        return user;
    }
}
