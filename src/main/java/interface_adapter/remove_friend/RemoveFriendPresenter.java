package interface_adapter.remove_friend;

import use_case.remove_friend.RemoveFriendOutputBoundary;
import use_case.remove_friend.RemoveFriendOutputData;

/**
 * Presenter for removing friends.
 */
public class RemoveFriendPresenter implements RemoveFriendOutputBoundary {
  private final RemoveFriendViewModel removeFriendViewModel;

  public RemoveFriendPresenter(final RemoveFriendViewModel removeFriendViewModel) {
    this.removeFriendViewModel = removeFriendViewModel;
  }

  @Override
  public void prepareSuccessView(final RemoveFriendOutputData outputData) {
    final RemoveFriendState removeFriendState = removeFriendViewModel.getState();
    removeFriendState.setMessage(outputData.getMessage());
    removeFriendState.setRemoveFriendSuccess(true);
    removeFriendState.setFriendUsername("");

    removeFriendViewModel.setState(removeFriendState);
    removeFriendViewModel.firePropertyChange();
  }

  @Override
  public void prepareFailView(final String error) {
    final RemoveFriendState removeFriendState = removeFriendViewModel.getState();
    removeFriendState.setMessage(error);
    removeFriendState.setRemoveFriendSuccess(false);
    removeFriendViewModel.setState(removeFriendState);
    removeFriendViewModel.firePropertyChange();
  }
}
