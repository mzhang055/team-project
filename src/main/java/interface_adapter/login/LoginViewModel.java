package interface_adapter.login;

public class LoginViewModel {
    private String message;
    private boolean success;
    private String currentUsername;

    public String getMessage(){return message; }
    public boolean isSuccess() {return success; }
    public String getCurrentUsername(){return currentUsername; }

    public void setMessage(String message){this.message = message; }
    public void setSuccess(boolean success){this.success = success; }
    public void setCurrentUsername(String currentUsername){this.currentUsername = currentUsername; }
}
