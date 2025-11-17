package use_case.accept_friend;

import entities.User;
import use_case.shared.UserDataAccessInterface;

public class AcceptFriendRequestInteractor implements AcceptFriendRequestInputBoundary {
    private final UserDataAccessInterface userDataAccess;
    private final AcceptFriendRequestOutputBoundary presenter;

    public AcceptFriendRequestInteractor(UserDataAccessInterface userDataAccess,
                                         AcceptFriendRequestOutputBoundary presenter){
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }
    public void execute(AcceptFriendRequestInputData inputData){
        String receiverUsername = inputData.getReceiverUsesrname();
        String requesterUsername = inputData.getRequesterUsername();

        if (!userDataAccess.existsByUsername(receiverUsername) ||
                !userDataAccess.existsByUsername(requesterUsername)){
            presenter.prepareFailView("User not found");
            return;
        }
        User receiver = userDataAccess.getUser(receiverUsername);
        User requester = userDataAccess.getUser(requesterUsername);

        if (!receiver.getIncomingFriendRequests().contains(requesterUsername)){
            presenter.prepareFailView("No such friend request");
            return;
        }
        if (receiver.getFriendsUsernames().contains(requesterUsername)){
            presenter.prepareFailView("You are already friends");
            return;
        }
        receiver.removeIncomingRequest(requesterUsername);
        requester.removeOutgoingRequest(receiverUsername);
        receiver.addFriend(requesterUsername);
        requester.addFriend(receiverUsername);
        userDataAccess.save(receiver);
        userDataAccess.save(requester);

        AcceptFriendRequestOutputData outputData = new AcceptFriendRequestOutputData(receiverUsername,
                requesterUsername, "Friend request accepted");
        presenter.prepareSuccessView(outputData);
    }
}
