package interface_adapter.logout;

import use_case.Logout.LogoutInputBoundary;
public class LogoutController {
    private LogoutInputBoundary LogoutUseCase;

    public LogoutController(LogoutInputBoundary logoutUseCase) {
        this.LogoutUseCase = logoutUseCase;
    }

    public void logout(String username) {
        LogoutUseCase.logout(username);
    }
}
