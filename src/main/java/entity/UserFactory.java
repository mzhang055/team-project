package entity;

/**
 * Factory for creating CommonUser objects.
 */
public class UserFactory {
    public User create(String username, String password) {return new User(username, password);}
}
