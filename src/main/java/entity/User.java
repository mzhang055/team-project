package entity;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String username;
    private String password;
    private final List<String> friendsUsernames = new ArrayList<>();

    public User(String username, String password) {
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
