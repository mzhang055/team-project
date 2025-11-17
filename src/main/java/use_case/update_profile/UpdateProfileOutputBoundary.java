package use_case.update_profile;

public interface UpdateProfileOutputBoundary {
    void prepareSuccessView(UpdateProfileOutputData outputData);
    void prepareFailView(String errorMessage);
}
