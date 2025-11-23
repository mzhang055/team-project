package use_case.remove_friend;

import entities.User;

public class RemoveFriendInteractor implements RemoveFriendInputBoundary{
    private final RemoveFriendUserDataAccessInterface userDataAccess;
    private final RemoveFriendOutputBoundary removeFriendPresenter;
    public RemoveFriendInteractor(RemoveFriendUserDataAccessInterface userDataAccess,
                                  RemoveFriendOutputBoundary removeFriendPresenter) {
        this.userDataAccess = userDataAccess;
        this.removeFriendPresenter = removeFriendPresenter;
    }

    @Override

    public void execute(RemoveFriendInputData inputData){
        String currentUsername = inputData.getCurrentUsername();
        String friendUsername = inputData.getFriendUsername();
        try{
            boolean userExists = userDataAccess.existsByName(friendUsername);
            if (userExists == false){
                removeFriendPresenter.prepareFailView("User" + friendUsername + " does not exist");
                return;
            }
            User currentUser = userDataAccess.getUser(currentUsername);
            User friendUser = userDataAccess.getUser(friendUsername);

            boolean isFriend = currentUser.getFriendsUsernames().contains(friendUsername);
            if (isFriend == false){
                removeFriendPresenter.prepareFailView(friendUsername + " is not friend");
                return;
            }
            currentUser.removeFriend(friendUsername);
            friendUser.removeFriend(currentUsername);

            userDataAccess.saveUser(currentUser);
            userDataAccess.saveUser(friendUser);

            RemoveFriendOutputData outputData = new RemoveFriendOutputData(
                    friendUsername,
                    true,
                    "Successfully removed " + friendUsername + " from friends."
            );
            removeFriendPresenter.prepareSuccessView(outputData);
        }catch (Exception e){
            removeFriendPresenter.prepareFailView("Failed to remove friend: " + e.getMessage());
        }
    }
}

