package use_case.Logout;

public class LogoutController {
    private LogoutInputBoundary interactor;

    public LogoutController(LogoutInputBoundary interactor) {
        this.interactor = interactor;
    }
    public void logout(String username) {
        interactor.logout(username);
    }
}
