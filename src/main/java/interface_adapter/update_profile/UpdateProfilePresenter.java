package interface_adapter.update_profile;

import use_case.update_profile.UpdateProfileOutputData;
import use_case.update_profile.UpdateProfileOutputBoundary;

public class UpdateProfilePresenter implements UpdateProfileOutputBoundary {
    private final UpdateProfileViewModel viewModel;

    public  UpdateProfilePresenter(UpdateProfileViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(UpdateProfileOutputData outputData) {
        UpdateProfileState state = viewModel.getState();

        state.setUsername(outputData.getUsername());
        state.setMessage(outputData.getMessage());
        state.setErrorMessage("");
        state.setUpdateEnabled(true);

        viewModel.setState(state);
        viewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        UpdateProfileState state = viewModel.getState();

        state.setErrorMessage(errorMessage);
        state.setMessage("");
        state.setUpdateEnabled(false);

        viewModel.setState(state);
        viewModel.firePropertyChange();
    }
}
