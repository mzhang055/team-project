package interface_adapter.remove_friend;

import use_case.remove_friend.RemoveFriendInputBoundary;
import use_case.remove_friend.RemoveFriendInputData;

/**
 * Controller for removing friends.
 */
public class RemoveFriendController {
  private RemoveFriendInputBoundary removeFriendUseCaseInteractor;

  public RemoveFriendController(final RemoveFriendInputBoundary removeFriendUseCaseInteractor) {
    this.removeFriendUseCaseInteractor = removeFriendUseCaseInteractor;
  }

  public void execute(final String currentUsername, final String friendUsername) {
    final RemoveFriendInputData inputData = new RemoveFriendInputData(currentUsername,
            friendUsername);
    removeFriendUseCaseInteractor.execute(inputData);
  }
}
