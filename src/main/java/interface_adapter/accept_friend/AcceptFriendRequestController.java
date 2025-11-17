package interface_adapter.accept_friend;

import use_case.accept_friend.AcceptFriendRequestInputBoundary;
import use_case.accept_friend.AcceptFriendRequestInputData;

public class AcceptFriendRequestController {
    private final AcceptFriendRequestInputBoundary interactor;
    public AcceptFriendRequestController(AcceptFriendRequestInputBoundary interactor){
        this.interactor = interactor;
    }
    public void accept(String receiverUsername, String requesterUsername){
        AcceptFriendRequestInputData input =
                new AcceptFriendRequestInputData(receiverUsername, requesterUsername);
        interactor.execute(input);
    }
}
