package interface_adapter.create_account;

import use_case.create_account.CreateAccountOutputBoundary;
import use_case.create_account.CreateAccountOutputData;

public class CreateAccountPresenter implements CreateAccountOutputBoundary {
    private final CreateAccountViewModel viewModel;
    public CreateAccountPresenter(CreateAccountViewModel viewModel){
        this.viewModel = viewModel;
    }
    public void prepareSuccessView(CreateAccountOutputData outputData){
        viewModel.setSuccess(true);
        viewModel.setMessage(outputData.getMessage());
    }
    public void prepareFailView(String errorMessage){
        viewModel.setSuccess(false);
        viewModel.setMessage(errorMessage);
    }
}
