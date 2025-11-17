package use_case.reject_friend;

import entities.User;
import use_case.shared.UserDataAccessInterface;
public class RejectFriendRequestInteractor implements RejectFriendRequestInputBoundary {
    private final UserDataAccessInterface userDataAccess;
    private final RejectFriendRequestOutputBoundary presenter;

    public RejectFriendRequestInteractor(UserDataAccessInterface userDataAccess,
                                         RejectFriendRequestOutputBoundary presenter){
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    public void execute(RejectFriendRequestInputData inputData){
        String receiverUsername = inputData.getReceiverUsername();
        String requesterUsername = inputData.getRequesterUsername();
        if (!userDataAccess.existsByUsername(receiverUsername) ||
                !userDataAccess.existsByUsername(requesterUsername)){
            presenter.prepareFailView("User not found");
            return;
        }
        User receiver = userDataAccess.getUser(receiverUsername);
        User requester = userDataAccess.getUser(requesterUsername);

        if (!receiver.getIncomingFriendRequests().contains(receiverUsername)){
            presenter.prepareFailView("No such friend request");
            return;
        }
        receiver.removeIncomingRequest(requesterUsername);
        requester.removeOutgoingRequest(receiverUsername);

        RejectFriendRequestOutputData outputData =
                new RejectFriendRequestOutputData(receiverUsername,
                        requesterUsername, "Friend request rejected");
        presenter.prepareSuccessView(outputData);
    }
}
