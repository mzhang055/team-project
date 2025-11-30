package app;

import data_access.InMemoryUserDataAccessObject;
import interface_adapter.create_account.CreateAccountController;
import interface_adapter.create_account.CreateAccountPresenter;
import interface_adapter.create_account.CreateAccountViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.logout.LogoutUI;
import interface_adapter.logout.LogoutViewModel;
import interface_adapter.logout.LogoutController;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutService;
import view.CreateAccountView;
import view.LoginView;
import use_case.create_account.CreateAccountInteractor;
import use_case.login.LoginInteractor;
import data_access.UserDataAccessInterface;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(Main::startApp);
    }
    private static void startApp(){
        UserDataAccessInterface userDataAccess = new InMemoryUserDataAccessObject();
        CreateAccountViewModel createAccountViewModel = new CreateAccountViewModel();
        CreateAccountPresenter createAccountPresenter = new CreateAccountPresenter(createAccountViewModel);
        CreateAccountInteractor createAccountInteractor =
                new CreateAccountInteractor(userDataAccess, createAccountPresenter);
        CreateAccountController createAccountController = new CreateAccountController(createAccountInteractor);

        LoginViewModel loginViewModel = new LoginViewModel();
        LoginPresenter loginPresenter = new LoginPresenter(loginViewModel);
        LoginInteractor loginInteractor = new LoginInteractor(userDataAccess, loginPresenter);
        LoginController loginController = new LoginController(loginInteractor);

        final LoginView[] loginViewHolder = new LoginView[1];
        final CreateAccountView[] createViewHolder = new CreateAccountView[1];

        loginViewHolder[0] = new LoginView(
                loginController, loginViewModel, ()->{
                    JOptionPane.showMessageDialog(loginViewHolder[0],
                            "Login successful for " + loginViewModel.getCurrentUsername());
                    // open mainboard here

        },
                ()->{
                    loginViewHolder[0].setVisible(false);
                    createViewHolder[0].setVisible(true);
                }
        );

        createViewHolder[0] = new CreateAccountView(
                createAccountController, createAccountViewModel, ()->{
                    createViewHolder[0].setVisible(false);
                    loginViewHolder[0].setVisible(true);
        },
                ()->{
                    createViewHolder[0].setVisible(false);
                    loginViewHolder[0].setVisible(true);
                }
        );

        loginViewHolder[0].setVisible(true);
    }

    private static LogoutUI setupLogoutForDashboard(LogoutViewModel logoutViewModel) {
        LogoutPresenter logoutPresenter = new LogoutPresenter(logoutViewModel);
        LogoutInteractor logoutInteractor = new LogoutInteractor(logoutPresenter);
        LogoutController logoutController = new LogoutController(logoutInteractor, logoutViewModel);
        LogoutService logoutService = new LogoutService(logoutInteractor, logoutViewModel);

        LogoutUI logoutUI = new LogoutUI(logoutController);

        logoutUI.addLogoutListener(e -> {
            System.out.println("Logout successful!");

        });

        return logoutUI;

    }
}
