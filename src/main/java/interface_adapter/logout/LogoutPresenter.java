package interface_adapter.logout;
import use_case.Logout.LogoutOutputBoundary;
import use_case.Logout.LogoutOutputData;

public class LogoutPresenter implements use_case.Logout.LogoutOutputBoundary {
    private final LogoutViewModel viewModel;
    public LogoutPresenter(LogoutViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @Override
    public void prepareSuccessView(LogoutOutputData data) {
        String username = data.getUsername();
        viewModel.setMessage("Bye" + username);
        System.out.println(viewModel.getMessage());
    }
    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setMessage(errorMessage);
        System.out.println(viewModel.getMessage());
    }
}
