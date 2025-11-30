package interface_adapter;

public class SessionManager {
    private String currentUsername;

    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public boolean isLoggedIn() {
        return currentUsername != null && !currentUsername.isEmpty();
    }

    public void clearSession() {
        currentUsername = null;
    }
}
