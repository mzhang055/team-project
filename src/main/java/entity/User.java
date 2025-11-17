package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple entity representing a user. Users have a username and password..
 */
public class User {
    private final String username;
    private String password;
    private final List<String> friendsUsernames = new ArrayList<>();

    /**
     * Creates a new user with the given non-empty name and non-empty password.
     * @param username the username
     * @param password the password
     * @throws IllegalArgumentException if the password or name are empty
     */
    public User(String username, String password) {
        if ("".equals(username)) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if ("".equals(password)) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public List<String> getFriendsUsernames() {
        return friendsUsernames;
    }

    public void addFriend(String friendUsername) {
        if (!friendsUsernames.contains(friendUsername)) {
            friendsUsernames.add(friendUsername);
        }
    }
}
