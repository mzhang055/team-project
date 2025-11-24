package interface_adapter.logout;

import use_case.logout.LogoutInputBoundary;
public class LogoutController {
    private LogoutInputBoundary LogoutUseCase;
    private LogoutViewModel viewModel;

    public LogoutController(LogoutInputBoundary logoutUseCase, LogoutViewModel viewModel) {
        this.LogoutUseCase = logoutUseCase;
        this.viewModel = viewModel;
    }

    public void logout(String username) {
        LogoutUseCase.logout(username);
    }
    public String getViewModelMessage() {
        return viewModel.getMessage();
    }
}
