package interface_adapter.add_friend;

import use_case.add_friend.AddFriendOutputBoundary;
import use_case.add_friend.AddFriendOutputData;

/**
 * Presenter for adding friends.
 */
public class AddFriendPresenter implements AddFriendOutputBoundary {
  private final AddFriendViewModel viewModel;

  public AddFriendPresenter(final AddFriendViewModel viewModel) {
    this.viewModel = viewModel;
  }

  public void prepareSuccessView(final AddFriendOutputData outputData) {
    viewModel.setSuccess(true);
    viewModel.setMessage(outputData.getMessage());
  }

  public void prepareFailView(final String errorMessage) {
    viewModel.setSuccess(false);
    viewModel.setMessage(errorMessage);
  }
}
