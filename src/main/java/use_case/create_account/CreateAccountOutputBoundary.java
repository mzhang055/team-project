package use_case.create_account;

public interface CreateAccountOutputBoundary {
    void prepareSuccessView(CreateAccountOutputData outputData);
    void prepareFailView(String errorMessage);
}
