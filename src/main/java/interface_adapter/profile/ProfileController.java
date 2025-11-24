package interface_adapter.profile;

import use_case.profile.ProfileInputData;
import use_case.profile.ProfileInputBoundary;

public class ProfileController {
    private final ProfileInputBoundary interactor;

    public ProfileController(ProfileInputBoundary interactor) { this.interactor = interactor; }

    public void loadProfile(String userId) {
        ProfileInputData profileInputData = new ProfileInputData(userId);
        interactor.loadProfile(profileInputData);
    }
}
