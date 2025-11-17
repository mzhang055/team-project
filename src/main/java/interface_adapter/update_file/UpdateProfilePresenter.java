package interface_adapter.update_file;

import use_case.update_profile.UpdateProfileOutputData;
import use_case.update_profile.UpdateProfileOutputBoundary;

public class UpdateProfilePresenter implements UpdateProfileOutputBoundary {
    private final UpdateProfileViewModel viewModel;
    public  UpdateProfilePresenter(UpdateProfileViewModel viewModel){
        this.viewModel = viewModel;
    }

    public void prepareSuccessView(UpdateProfileOutputData outputData){
        viewModel.setSuccess(true);
        viewModel.setMessage(outputData.getMessage());
    }
    public void prepareFailView(String errorMessage){
        viewModel.setSuccess(false);
        viewModel.setMessage(errorMessage);
    }
}
