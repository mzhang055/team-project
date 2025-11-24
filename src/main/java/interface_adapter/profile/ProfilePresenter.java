package interface_adapter.profile;

import interface_adapter.ViewManagerModel;
import use_case.profile.ProfileOutputBoundary;
import use_case.profile.ProfileOutputData;
import view.ProfileView;

public class ProfilePresenter implements ProfileOutputBoundary {
    private final ProfileViewModel profileViewModel;
    private final ViewManagerModel viewManagerModel;

    public ProfilePresenter(ProfileViewModel profileViewModel, ViewManagerModel viewManagerModel) {
        this.profileViewModel = profileViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void updateProfile(ProfileOutputData profileOutputData) {
        String username = profileOutputData.getUsername();
        String password = profileOutputData.getPassword();
        double height = profileOutputData.getHeight();
        double weight = profileOutputData.getWeight();
        String allergies = profileOutputData.getAllergies();
        boolean vegan = profileOutputData.isVegan();

        ProfileState state = new ProfileState();
        state.setUsername(username);
        state.setPassword(password);
        state.setHeight(height);
        state.setWeight(weight);
        state.setAllergies(allergies);
        state.setVegan(vegan);

        profileViewModel.setState(state);
        profileViewModel.firePropertyChange("Profile");
    }

}
