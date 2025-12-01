package interface_adapter.logout;

import use_case.logout.LogoutInputBoundary;

public class LogoutController {
    private LogoutInputBoundary logoutUseCase;
    private LogoutViewModel viewModel;

    public LogoutController(LogoutInputBoundary logoutUseCase, LogoutViewModel viewModel) {
        this.logoutUseCase = logoutUseCase;
        this.viewModel = viewModel;

    }

    public boolean logoutCurrentUser(){
        try{
            String currentUsername = viewModel.getCurrentUsername();
            logoutUseCase.logout(currentUsername);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean logoutUser(String username){
        try{
            logoutUseCase.logout(username);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public String getLogoutMessage(){
        return viewModel.getMessage();
    }
}
