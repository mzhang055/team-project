package use_case.reject_friend;

public interface RejectFriendRequestOutputBoundary {
    void prepareSuccessView(RejectFriendRequestOutputData outputData);
    void prepareFailView(String errorMessage);
}
