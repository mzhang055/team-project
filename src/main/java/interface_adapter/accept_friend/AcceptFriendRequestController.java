package interface_adapter.accept_friend;

import use_case.accept_friend.AcceptFriendRequestInputBoundary;
import use_case.accept_friend.AcceptFriendRequestInputData;

/**
 * Controller for accepting friend requests.
 */
public class AcceptFriendRequestController {
  private final AcceptFriendRequestInputBoundary interactor;

  public AcceptFriendRequestController(final AcceptFriendRequestInputBoundary interactor) {
    this.interactor = interactor;
  }

  public void accept(final String receiverUsername, final String requesterUsername) {
    final AcceptFriendRequestInputData input =
        new AcceptFriendRequestInputData(receiverUsername, requesterUsername);
    interactor.execute(input);
  }
}
