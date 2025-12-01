package interface_adapter.add_friend;

import use_case.add_friend.AddFriendInputData;
import use_case.add_friend.AddFriendInputBoundary;

public class AddFriendController {
    private final AddFriendInputBoundary interactor;

    public AddFriendController(AddFriendInputBoundary interactor){
        this.interactor = interactor;
    }
    public void addFriend(String requesterUsername, String targetUsername){
        AddFriendInputData input = new AddFriendInputData(requesterUsername, targetUsername);
        interactor.execute(input);
    }
}
