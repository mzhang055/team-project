package use_case.accept_friend;

public interface AcceptFriendRequestOutputBoundary {
    void prepareSuccessView(AcceptFriendRequestOutputData outputData);
    void prepareFailView(String errorMessage);
}
