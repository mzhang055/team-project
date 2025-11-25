package interface_adapter.logout;
import use_case.logout.LogoutInteractor;

public class LogoutMain {public static void main(String[] args) {
    LogoutViewModel viewModel = new LogoutViewModel();
    LogoutPresenter presenter = new LogoutPresenter(viewModel);
    LogoutInteractor interactor = new LogoutInteractor(presenter);
    LogoutController controller = new LogoutController(interactor, viewModel);
    new LogoutUI(controller);
}
}
