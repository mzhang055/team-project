package data_access;

import entity.User;
import use_case.shared.UserDataAccessInterface;
import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDataAccessObject implements UserDataAccessInterface {
    private final Map<String, User> users = new HashMap<>();
    public boolean existsByUsername(String username){
        return users.containsKey(username);
    }
    public User getUser(String username){
        return users.get(username);
    }
    public void save(User user){
        users.put(user.getUsername(), user);
    }
}
