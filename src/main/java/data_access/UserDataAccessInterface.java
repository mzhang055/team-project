package data_access;

import entities.User;
import java.util.List;

public interface UserDataAccessInterface {
    boolean existsByUsername(String username);

    User getUser(String username);

    void save(User user);

    List<User> getAllUsers();
}
