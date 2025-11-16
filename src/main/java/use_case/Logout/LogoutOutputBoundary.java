package use_case.Logout;

public interface LogoutOutputBoundary {
    void prepareSuccessView(LogoutOutputData data);
    void prepareFailView(String errorMessage);
}
