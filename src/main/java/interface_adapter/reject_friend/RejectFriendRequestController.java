package interface_adapter.reject_friend;

import use_case.reject_friend.RejectFriendRequestInputData;
import use_case.reject_friend.RejectFriendRequestInputBoundary;

public class RejectFriendRequestController {
    private final RejectFriendRequestInputBoundary interactor;
    public RejectFriendRequestController(RejectFriendRequestInputBoundary interactor){
        this.interactor = interactor;
    }
    public void reject(String receiverUsername, String requesterUsername){
        RejectFriendRequestInputData input =
                new RejectFriendRequestInputData(receiverUsername, requesterUsername);
        interactor.execute(input);
    }
}
