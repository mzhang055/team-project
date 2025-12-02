package interface_adapter.add_friend;

import use_case.add_friend.AddFriendInputBoundary;
import use_case.add_friend.AddFriendInputData;

/**
 * Controller for adding friends.
 */
public class AddFriendController {
  private final AddFriendInputBoundary interactor;

  public AddFriendController(final AddFriendInputBoundary interactor) {
    this.interactor = interactor;
  }

  public void addFriend(final String requesterUsername, final String targetUsername) {
    final AddFriendInputData input = new AddFriendInputData(requesterUsername, targetUsername);
    interactor.execute(input);
  }
}
