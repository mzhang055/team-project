package use_case.logout;

public interface LogoutOutputBoundary {
    void prepareSuccessView(LogoutOutputData data);
    void prepareFailView(String errorMessage);
}
