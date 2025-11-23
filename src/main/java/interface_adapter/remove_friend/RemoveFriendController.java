package interface_adapter.remove_friend;

import use_case.remove_friend.RemoveFriendInputBoundary;
import use_case.remove_friend.RemoveFriendInputData;

public class RemoveFriendController {
    private RemoveFriendInputBoundary removeFriendUseCaseInteractor;
    public RemoveFriendController(RemoveFriendInputBoundary removeFriendUseCaseInteractor) {
        this.removeFriendUseCaseInteractor = removeFriendUseCaseInteractor;
    }
    public void execute(String currentUsername, String friendUsername) {
        RemoveFriendInputData inputData = new RemoveFriendInputData(currentUsername,
                friendUsername);
        removeFriendUseCaseInteractor.execute(inputData);
    }
}
