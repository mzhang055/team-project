package use_case.add_friend;

import entities.User;
import use_case.shared.UserDataAccessInterface;

public class AddFriendInteractor {
    private final UserDataAccessInterface userDataAccess;
    private final AddFriendOutputBoundary presenter;

    public AddFriendInteractor(UserDataAccessInterface userDataAccess, AddFriendOutputBoundary presenter){
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }
    public void execute(AddFriendInputData inputData){
        String requesterUsername = inputData.getRequesterUsername();
        String targetUsername = inputData.getTargetUsername();

        if (requesterUsername.equals(targetUsername)){
            presenter.prepareFailView("You cannot add yourself");
            return;
        }
        if (!userDataAccess.existsByUsername(requesterUsername) || !userDataAccess.existsByUsername(targetUsername)){
            presenter.prepareFailView("User not found");
            return;
        }

        User requester = userDataAccess.getUser(requesterUsername);
        User target = userDataAccess.getUser(targetUsername);

        if (requester.getFriendsUsernames().contains(targetUsername)){
            presenter.prepareFailView("You are already friends");
            return;
        }
        if (target.getIncomingFriendRequests().contains(requesterUsername)){
            presenter.prepareFailView("Friend request already sent");
            return;
        }

        requester.addOutgoingRequest(targetUsername);
        target.addIncomingRequest(requesterUsername);

        userDataAccess.save(requester);
        userDataAccess.save(target);

        AddFriendOutputData outputData = new AddFriendOutputData(requesterUsername, targetUsername, "Friend request sent");
        presenter.prepareSuccessView(outputData);
    }
}
