package interface_adapter.login;

import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;
import interface_adapter.Navigation;

public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel viewModel;
    private Navigation navigation;

    public LoginPresenter(LoginViewModel viewModel, Navigation navigation){
        this.viewModel = viewModel;
        this.navigation = navigation;
    }
    public void prepareSuccessView(LoginOutputData outputData){
        viewModel.setSuccess(true);
        viewModel.setCurrentUsername(outputData.getUsername());
        viewModel.setMessage(outputData.getMessage());
        navigation.setCurrentUsername(outputData.getUsername());
    }
    public void prepareFailView(String errorMessage){
        viewModel.setSuccess(false);
        viewModel.setMessage(errorMessage);
    }
}
