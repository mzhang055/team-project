package interface_adapter.accept_friend;

import use_case.accept_friend.AcceptFriendRequestOutputBoundary;
import use_case.accept_friend.AcceptFriendRequestOutputData;

/**
 * Presenter for accepting friend requests.
 */
public class AcceptFriendRequestPresenter implements AcceptFriendRequestOutputBoundary {
  private final AcceptFriendRequestViewModel viewModel;

  public AcceptFriendRequestPresenter(final AcceptFriendRequestViewModel viewModel) {
    this.viewModel = viewModel;
  }

  public void prepareSuccessView(final AcceptFriendRequestOutputData outputData) {
    viewModel.setSuccess(true);
    viewModel.setMessage(outputData.getMessage());
  }

  public void prepareFailView(final String errorMessage) {
    viewModel.setSuccess(false);
    viewModel.setMessage(errorMessage);
  }
}
