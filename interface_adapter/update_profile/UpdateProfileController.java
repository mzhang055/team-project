package interface_adapter.update_profile;

import interface_adapter.SessionManager;
import use_case.update_profile.UpdateProfileInputData;
import use_case.update_profile.UpdateProfileInputBoundary;

public class UpdateProfileController {
    private final UpdateProfileInputBoundary interactor;
    private final SessionManager sessionManager;

    public UpdateProfileController(UpdateProfileInputBoundary interactor, SessionManager sessionManager) {
        this.interactor = interactor;
        this.sessionManager = sessionManager;
    }

    public void updateProfile(double height, double weight,
                              String allergies, boolean vegan){
        String username = sessionManager.getCurrentUsername();
        UpdateProfileInputData input = new UpdateProfileInputData(username, height, weight,
                allergies, vegan);
        interactor.execute(input);
    }
}
