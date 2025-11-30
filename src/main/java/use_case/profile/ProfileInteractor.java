package use_case.profile;

import data_access.UserDataAccessInterface;
import entities.User;

public class ProfileInteractor implements ProfileInputBoundary {
    private final UserDataAccessInterface userDataAccess;
    private final ProfileOutputBoundary presenter;

    public ProfileInteractor(UserDataAccessInterface userDataAccess, ProfileOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void loadProfile(ProfileInputData input) {
        String userId = input.getUserId();
        User user = userDataAccess.getUser(userId);
        String password = user.getPassword();
        double height = user.getHeight();
        double weight = user.getWeight();
        String allergies = user.getAllergies();
        boolean vegan = user.isVegan();
        ProfileOutputData output = new ProfileOutputData(userId, password, height, weight, allergies, vegan);
        presenter.updateProfile(output);
    }
}
