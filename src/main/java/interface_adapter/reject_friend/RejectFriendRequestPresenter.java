package interface_adapter.reject_friend;

import use_case.reject_friend.RejectFriendRequestOutputData;
import use_case.reject_friend.RejectFriendRequestOutputBoundary;

public class RejectFriendRequestPresenter implements RejectFriendRequestOutputBoundary {
    private final RejectFriendRequestViewModel viewModel;
    public RejectFriendRequestPresenter(RejectFriendRequestViewModel viewModel){
        this.viewModel = viewModel;
    }
    public void prepareSuccessView(RejectFriendRequestOutputData outputData){
        viewModel.setSuccess(true);
        viewModel.setMessage(outputData.getMessage());
    }
    public void prepareFailView(String errorMessage){
        viewModel.setSuccess(false);
        viewModel.setMessage(errorMessage);
    }
}
