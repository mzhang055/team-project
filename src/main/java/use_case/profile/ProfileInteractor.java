package use_case.profile;

import data_access.InMemoryUserDataAccessObject;
import entities.User;

public class ProfileInteractor implements ProfileInputBoundary {
    private final InMemoryUserDataAccessObject inMemoryUserDataAccessObject;
    private final ProfileOutputBoundary presenter;

    public ProfileInteractor(InMemoryUserDataAccessObject inMemoryUserDataAccessObject, ProfileOutputBoundary presenter) {
        this.inMemoryUserDataAccessObject = inMemoryUserDataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void loadProfile(ProfileInputData input) {
        String userId = input.getUserId();
        User user = inMemoryUserDataAccessObject.getUser(userId);
        String password = user.getPassword();
        double height = user.getHeight();
        double weight = user.getWeight();
        String allergies = user.getAllergies();
        if (allergies.isEmpty()) {
            allergies = "None";
        }
        boolean vegan = user.isVegan();
        ProfileOutputData output = new ProfileOutputData(userId, password, height, weight, allergies, vegan);
        presenter.updateProfile(output);
    }
}
