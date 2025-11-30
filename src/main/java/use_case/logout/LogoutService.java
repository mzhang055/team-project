package use_case.logout;

import interface_adapter.logout.LogoutViewModel;

public class LogoutService implements LogoutServiceInterface {
    private final LogoutInputBoundary logoutInteractor;
    private final LogoutViewModel viewModel;

    public LogoutService(LogoutInputBoundary logoutInteractor, LogoutViewModel viewModel) {
        this.logoutInteractor = logoutInteractor;
        this.viewModel = viewModel;
    }

    @Override
    public boolean logoutUser(String username){
        try {
            logoutInteractor.logout(username);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean logoutCurrentUser(){
        try{
            String currentUsername = viewModel.getCurrentUsername();
            if (currentUsername != null && !currentUsername.isEmpty()){
                logoutInteractor.logout(currentUsername);
                return true;
            }
            return false;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public String getLogoutMessage(){
        return viewModel.getMessage();
    }
}
