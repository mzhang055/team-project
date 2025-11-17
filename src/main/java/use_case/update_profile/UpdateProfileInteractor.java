package use_case.update_profile;

import entities.User;
import use_case.shared.UserDataAccessInterface;
public class UpdateProfileInteractor implements UpdateProfileInputBoundary {
    private final UserDataAccessInterface userDataAccess;
    private final UpdateProfileOutputBoundary presenter;

    public UpdateProfileInteractor(UserDataAccessInterface userDataAccess,
                                   UpdateProfileOutputBoundary presenter){
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }
    public void execute(UpdateProfileInputData inputData){
        String username = inputData.getUsername();
        if (!userDataAccess.existsByUsername(username)){
            presenter.prepareFailView("User not found");
            return;
        }
        User user = userDataAccess.getUser(username);

        user.setHeight(inputData.getHeight());
        user.setWeight(inputData.getWeight());
        user.setAllergies(inputData.getAllergies());
        user.setVegan(inputData.isVegan());

        userDataAccess.save(user);
        UpdateProfileOutputData outputData =
                new UpdateProfileOutputData(username, "Profile updated");
        presenter.prepareSuccessView(outputData);
    }
}
