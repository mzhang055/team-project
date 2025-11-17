package interface_adapter.update_file;

import use_case.update_profile.UpdateProfileInputData;
import use_case.update_profile.UpdateProfileInputBoundary;

public class UpdateProfileController {
    private final UpdateProfileInputBoundary interactor;

    public UpdateProfileController(UpdateProfileInputBoundary interactor){
        this.interactor = interactor;
    }
    public void updateProfile(String username, double height, double weight,
                              String allergies, boolean vegan){
        UpdateProfileInputData input = new UpdateProfileInputData(username, height, weight,
                allergies, vegan);
        interactor.execute(input);
    }
}
