package use_case.login;

import entity.User;
import use_case.shared.UserDataAccessInterface;

public class LoginInteractor implements LoginInputBoundary {
    private final UserDataAccessInterface userDataAccess;
    private final LoginOutputBoundary presenter;
    public LoginInteractor(UserDataAccessInterface userDataAccess, LoginOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }
    public void execute(LoginInputData inputData) {
        String username = inputData.getUsername();
        String password = inputData.getPassword();
        if (!userDataAccess.existsByUsername(username)){
            presenter.prepareFailView("User not found");
            return;
        }
        User user = userDataAccess.getUser(username);
        if (!user.getPassword().equals(password)) {
            presenter.prepareFailView("Incorrect password");
            return;
        }
        LoginOutputData outputData = new LoginOutputData(username, "Login successful");
        presenter.prepareSuccessView(outputData);
    }
}
