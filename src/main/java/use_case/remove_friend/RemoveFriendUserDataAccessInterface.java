package use_case.remove_friend;

import entities.User;

public interface RemoveFriendUserDataAccessInterface {
    boolean existsByName(String username);
    User getUser(String username);
    void saveUser(User user);
}
