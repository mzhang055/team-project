package interface_adapter.login;

import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel viewModel;

    public LoginPresenter(LoginViewModel viewModel){
        this.viewModel = viewModel;
    }
    public void prepareSuccessView(LoginOutputData outputData){
        viewModel.setSuccess(true);
        viewModel.setCurrentUsername(outputData.getUsername());
        viewModel.setMessage(outputData.getMessage());
    }
    public void prepareFailView(String errorMessage){
        viewModel.setSuccess(false);
        viewModel.setMessage(errorMessage);
    }
}
