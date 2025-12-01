package use_case.remove_friend;

public interface RemoveFriendOutputBoundary {
    void prepareSuccessView(RemoveFriendOutputData outputData);
    void prepareFailView(String error);
}
