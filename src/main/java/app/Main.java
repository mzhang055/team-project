package app;

import data_access.InMemoryUserDataAccessObject;
import data_access.UserDataAccessInterface;
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

import interface_adapter.view_leaderboard.LeaderboardController;
import interface_adapter.view_leaderboard.LeaderboardPresenter;
import interface_adapter.view_leaderboard.LeaderboardViewmodel;
import use_case.view_leaderboard.LeaderboardDataAccess;
import use_case.view_leaderboard.LeaderboardDataAccessInterface;
import use_case.view_leaderboard.LeaderboardInteractor;

import interface_adapter.remove_friend.RemoveFriendController;
import interface_adapter.remove_friend.RemoveFriendPresenter;
import interface_adapter.remove_friend.RemoveFriendViewModel;
import use_case.remove_friend.RemoveFriendInteractor;

import view.CreateAccountView;
import view.LeaderboardView;
import view.LoginView;
import view.DashboardView;

import interface_adapter.add_friend.AddFriendController;
import interface_adapter.add_friend.AddFriendPresenter;
import interface_adapter.add_friend.AddFriendViewModel;

import use_case.create_account.CreateAccountInteractor;
import use_case.login.LoginInteractor;
import use_case.add_friend.AddFriendInteractor;

import javax.swing.*;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(Main::startApp);
    }

    private static void startApp() {
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

        AddFriendViewModel addFriendViewModel = new AddFriendViewModel();
        AddFriendPresenter addFriendPresenter = new AddFriendPresenter(addFriendViewModel);
        AddFriendInteractor addFriendInteractor = new AddFriendInteractor(userDataAccess, addFriendPresenter);
        AddFriendController addFriendController = new AddFriendController(addFriendInteractor);

        // viewManager.showLoginView();
        LeaderboardViewmodel leaderboardViewModel = new LeaderboardViewmodel();
        LeaderboardPresenter leaderboardPresenter = new LeaderboardPresenter(leaderboardViewModel);

        LeaderboardDataAccessInterface leaderboardDataAccess = new LeaderboardDataAccess();
        LeaderboardInteractor leaderboardInteractor = new LeaderboardInteractor(leaderboardPresenter, leaderboardDataAccess);
        LeaderboardController leaderboardController = new LeaderboardController(leaderboardInteractor);

        LogoutViewModel logoutViewModel = new LogoutViewModel();
        LogoutPresenter logoutPresenter = new LogoutPresenter(logoutViewModel);
        LogoutInteractor logoutInteractor = new LogoutInteractor(logoutPresenter);
        LogoutController logoutController = new LogoutController(logoutInteractor, logoutViewModel);


        LeaderboardView leaderboardView = new LeaderboardView(null, "defaultUser");
        LogoutUI logoutUI = setupLogoutForDashboard(logoutController);


    }

    private static LogoutUI setupLogoutForDashboard(LogoutController logoutController) {

        LogoutUI logoutUI = new LogoutUI(logoutController);

        logoutUI.addLogoutListener(e -> {
            System.out.println("Logout successful!");

        });

        return logoutUI;

    }

}
