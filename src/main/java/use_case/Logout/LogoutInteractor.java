package use_case.Logout;

public class LogoutInteractor implements LogoutInputBoundary {
    private LogoutOutputBoundary presenter;

    public LogoutInteractor(LogoutOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void logout(String username) {
        if (username == null || username.isEmpty()) {
            presenter.prepareFailView("Username is empty");
        } else {
            presenter.prepareSuccessView(new LogoutOutputData(username));
        }
    }
}
