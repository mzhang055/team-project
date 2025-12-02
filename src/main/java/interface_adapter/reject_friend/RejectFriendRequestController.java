package interface_adapter.reject_friend;

import use_case.reject_friend.RejectFriendRequestInputBoundary;
import use_case.reject_friend.RejectFriendRequestInputData;

/**
 * Controller for rejecting friend requests.
 */
public class RejectFriendRequestController {
  private final RejectFriendRequestInputBoundary interactor;

  public RejectFriendRequestController(final RejectFriendRequestInputBoundary interactor) {
    this.interactor = interactor;
  }

  public void reject(final String receiverUsername, final String requesterUsername) {
    final RejectFriendRequestInputData input =
        new RejectFriendRequestInputData(receiverUsername, requesterUsername);
    interactor.execute(input);
  }
}
