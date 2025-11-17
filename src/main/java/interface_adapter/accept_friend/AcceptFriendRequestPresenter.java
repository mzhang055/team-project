package interface_adapter.accept_friend;

import use_case.accept_friend.AcceptFriendRequestOutputBoundary;
import use_case.accept_friend.AcceptFriendRequestOutputData;

public class AcceptFriendRequestPresenter implements AcceptFriendRequestOutputBoundary {
    private final AcceptFriendRequestViewModel viewModel;

    public AcceptFriendRequestPresenter(AcceptFriendRequestViewModel viewModel){
        this.viewModel = viewModel;
    }
    public void prepareSuccessView(AcceptFriendRequestOutputData outputData){
        viewModel.setSuccess(true);
        viewModel.setMessage(outputData.getMessage());
    }
    public void prepareFailView(String errorMessage){
        viewModel.setSuccess(false);
        viewModel.setMessage(errorMessage);
    }
}
