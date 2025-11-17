package use_case.add_friend;

public interface AddFriendOutputBoundary {
    void prepareSuccessView(AddFriendOutputData outputData);
    void prepareFailView(String errorMessage);
}
