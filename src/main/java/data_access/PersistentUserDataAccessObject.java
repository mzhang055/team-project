package data_access;

import entities.User;

public class PersistentUserDataAccessObject implements UserDataAccessInterface {
    private final UserDataAccessInterface localDAO;
    private final RemoteAuthGateway remoteAuthGateway;

    public PersistentUserDataAccessObject(UserDataAccessInterface localDAO,
                                          RemoteAuthGateway remoteAuthGateway) {
        this.localDAO = localDAO;
        this.remoteAuthGateway = remoteAuthGateway;
    }

    public  boolean existsByUsername(String username) {
        if (remoteAuthGateway.userExists(username)) return true;
        return localDAO.existsByUsername(username);
    }

    public User getUser(String username) {
        User local = localDAO.getUser(username);
        if (local != null) return local;

        String remotePassword = remoteAuthGateway.getPassword(username);
        if (remotePassword == null) return null;

        User created = new User(username, remotePassword,
                0.0, 0.0, "", false);
        localDAO.save(created);
        return created;
    }

    public void save(User user) {
        localDAO.save(user);
        boolean remoteExists = remoteAuthGateway.userExists(user.getUsername());
        try {
            if (!remoteExists) {
                remoteAuthGateway.createRemoteUser(user.getUsername(), user.getPassword());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to sync", e);
        }
    }
}
