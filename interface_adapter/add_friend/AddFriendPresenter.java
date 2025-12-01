package interface_adapter.add_friend;

import use_case.add_friend.AddFriendOutputData;
import use_case.add_friend.AddFriendOutputBoundary;

public class AddFriendPresenter implements AddFriendOutputBoundary {
    private final AddFriendViewModel viewModel;

    public AddFriendPresenter(AddFriendViewModel viewModel){
        this.viewModel = viewModel;
    }
    public void prepareSuccessView(AddFriendOutputData outputData){
        viewModel.setSuccess(true);
        viewModel.setMessage(outputData.getMessage());
    }
    public void prepareFailView(String errorMessage){
        viewModel.setSuccess(false);
        viewModel.setMessage(errorMessage);
    }
}
