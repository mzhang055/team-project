package use_case.logout;

public interface LogoutServiceInterface {
    boolean logoutUser(String username);
    boolean logoutCurrentUser();
    String getLogoutMessage();
}
