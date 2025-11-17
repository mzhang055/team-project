package use_case.create_account;

import entities.User;
import use_case.shared.UserDataAccessInterface;

public class CreateAccountInteractor implements CreateAccountInputBoundary {
    private final UserDataAccessInterface userDataAccess;
    private final CreateAccountOutputBoundary presenter;

    public CreateAccountInteractor(UserDataAccessInterface userDataAccess,
                                   CreateAccountOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    public void execute(CreateAccountInputData inputData) {
        String username = inputData.getUsername();
        String password = inputData.getPassword();
        if (username == null || password.isEmpty()){
            presenter.prepareFailView("Missing input");
            return;
        }
        if (userDataAccess.existsByUsername(username)){
            presenter.prepareFailView("Username already registered");
            return;
        }
        User newUser = new User(username, password);
        userDataAccess.save(newUser);
        CreateAccountOutputData outputData = new CreateAccountOutputData(username, "User registered");
        presenter.prepareSuccessView(outputData);

    }
}
