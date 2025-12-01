package interface_adapter.update_profile;

public class UpdateProfileState {
    private String username = "";
    private String message = "";
    private String errorMessage = "";
    private boolean updateEnabled = true;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public boolean isUpdateEnabled() { return updateEnabled; }
    public void setUpdateEnabled(boolean updateEnabled) { this.updateEnabled = updateEnabled; }
}
