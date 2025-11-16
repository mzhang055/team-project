package use_case.shared;

import entity.User;

public interface UserDataAccessInterface {
    boolean existsByUsername(String username);

    User getUser(String username);

    void save(User user);
}
