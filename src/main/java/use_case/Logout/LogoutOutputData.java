package use_case.Logout;

public class LogoutOutputData {
    private final String username;
    public LogoutOutputData(String username){
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
}
