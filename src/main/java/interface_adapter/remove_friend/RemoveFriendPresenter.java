package interface_adapter.remove_friend;

import use_case.remove_friend.RemoveFriendInputBoundary;
import use_case.remove_friend.RemoveFriendOutputBoundary;
import use_case.remove_friend.RemoveFriendOutputData;

public class RemoveFriendPresenter implements RemoveFriendOutputBoundary {
    private final RemoveFriendViewModel removeFriendViewModel;
    public RemoveFriendPresenter(RemoveFriendViewModel removeFriendViewModel) {
        this.removeFriendViewModel = removeFriendViewModel;
    }

    @Override
    public void prepareSuccessView(RemoveFriendOutputData outputData) {
        RemoveFriendState removeFriendState = removeFriendViewModel.getState();
        removeFriendState.setMessage(outputData.getMessage());
        removeFriendState.setRemoveFriendSuccess(true);
        removeFriendState.setFriendUsername("");

        removeFriendViewModel.setState(removeFriendState);
        removeFriendViewModel.firePropertyChange();
    }
    @Override
    public void prepareFailView(String error) {
        RemoveFriendState removeFriendState = removeFriendViewModel.getState();
        removeFriendState.setMessage(error);
        removeFriendState.setRemoveFriendSuccess(false);
        removeFriendViewModel.setState(removeFriendState);
        removeFriendViewModel.firePropertyChange();
    }
}
