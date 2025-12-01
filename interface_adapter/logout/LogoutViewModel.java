package interface_adapter.logout;


public class LogoutViewModel {
    private String message;
    private String currentUsername;

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }
    public String getCurrentUsername() {
        return currentUsername;
    }
}
