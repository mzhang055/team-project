package app;

import data_access.InMemoryMealDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import data_access.MealDataAccessInterface;
import data_access.UserDataAccessInterface;
import entities.User;
import entities.UserFactory;
import interface_adapter.LegacyViewManagerModel;
import interface_adapter.Navigation;
import interface_adapter.SessionManager;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_friend.AddFriendController;
import interface_adapter.add_friend.AddFriendPresenter;
import interface_adapter.add_friend.AddFriendViewModel;
import interface_adapter.create_account.CreateAccountController;
import interface_adapter.create_account.CreateAccountPresenter;
import interface_adapter.create_account.CreateAccountViewModel;
import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardPresenter;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.log_meals.CalorieNinjasApiClient;
import interface_adapter.log_meals.LogMealsController;
import interface_adapter.log_meals.LogMealsPresenter;
import interface_adapter.log_meals.LogMealsViewModel;
import interface_adapter.profile.ProfileController;
import interface_adapter.profile.ProfilePresenter;
import interface_adapter.profile.ProfileViewModel;
import use_case.add_friend.AddFriendInputBoundary;
import use_case.add_friend.AddFriendInteractor;
import use_case.add_friend.AddFriendOutputBoundary;
import use_case.create_account.CreateAccountInputBoundary;
import use_case.create_account.CreateAccountInteractor;
import use_case.create_account.CreateAccountOutputBoundary;
import use_case.dashboard.DashboardInputBoundary;
import use_case.dashboard.DashboardInteractor;
import use_case.dashboard.DashboardOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.log_meals.LogMealsInputBoundary;
import use_case.log_meals.LogMealsInteractor;
import use_case.log_meals.LogMealsOutputBoundary;
import use_case.profile.ProfileInputBoundary;
import use_case.profile.ProfileInteractor;
import use_case.profile.ProfileOutputBoundary;
import view.*;
import view.DashboardView;
import view.LogMealsView;
import view.ProfileView;
import view.RecipeMenuView;
import view.ViewManager;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final JPanel legacy = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final UserFactory userFactory = new UserFactory();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    final LegacyViewManagerModel legacyVMM = new LegacyViewManagerModel(legacy);
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel,legacyVMM);

    InMemoryUserDataAccessObject userDataAccess = new InMemoryUserDataAccessObject();
    InMemoryMealDataAccessObject mealDataAccess = new InMemoryMealDataAccessObject();

    SessionManager sessionManager = new SessionManager();
    Navigation navigation = new Navigation(viewManagerModel,legacyVMM,sessionManager);

    User user = new User("Cherry", "Red");

    private LoginViewModel loginViewModel;
    private LoginView loginView;
    private CreateAccountViewModel createAccountViewModel;
    private CreateAccountView createAccountView;
    private AddFriendViewModel addFriendViewModel;
    private AddFriendView  addFriendView;
    private DashboardView dashboardView;
    private DashboardViewModel dashboardViewModel;
    private ProfileView profileView;
    private ProfileViewModel profileViewModel;
    private RecipeMenuView recipeMenuView;
    private LogMealsView logMealsView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        cardPanel.add(legacy, "Legacy");
        userDataAccess.save(user);

        RecipeModuleBuilder recipeBuilder = new RecipeModuleBuilder(userDataAccess);
        this.recipeMenuView = recipeBuilder.buildRecipeMenuView(user.getUsername());
    }

    public AppBuilder addLoginView(){
        this.loginViewModel = new LoginViewModel();
        this.loginView = new LoginView(loginViewModel, navigation);
        legacyVMM.addView("Login", loginView);
        return this;
    }

    public AppBuilder addCreateAccountView(){
        this.createAccountViewModel = new CreateAccountViewModel();
        this.createAccountView = new CreateAccountView(createAccountViewModel, navigation);
        legacyVMM.addView("Create Account", createAccountView);
        return this;
    }

    public AppBuilder addAddFriendView(){
        this.addFriendViewModel = new AddFriendViewModel();
        this.addFriendView = new AddFriendView(addFriendViewModel, navigation);
        legacyVMM.addView("Add Friend", addFriendView);
        return this;
    }

    public AppBuilder addDashboardView() {
        this.dashboardViewModel = new DashboardViewModel();
        this.dashboardView = new DashboardView(dashboardViewModel, navigation, recipeMenuView);
        cardPanel.add(dashboardView, dashboardView.getViewName());
        return this;
    }

    public AppBuilder addProfileView() {
        this.profileViewModel = new ProfileViewModel();
        this.profileView =new ProfileView(profileViewModel, navigation);
        cardPanel.add(profileView, profileView.getViewName());
        return this;
    }

    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginPresenter = new LoginPresenter(loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(userDataAccess, loginPresenter);
        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    public AppBuilder addCreateAccountUseCase() {
        final CreateAccountOutputBoundary createAccountPresenter = new CreateAccountPresenter(createAccountViewModel);
        final CreateAccountInputBoundary createAccountInteractor = new CreateAccountInteractor(userDataAccess, createAccountPresenter);
        final CreateAccountController createAccountController = new CreateAccountController(createAccountInteractor);
        createAccountView.setCreateAccountController(createAccountController);
        return this;
    }

    public AppBuilder addDashboardUseCase() {
       final DashboardOutputBoundary dashboardPresenter = new DashboardPresenter(dashboardViewModel, viewManagerModel);
       final DashboardInputBoundary dashboardInteractor = new DashboardInteractor(userDataAccess,mealDataAccess, dashboardPresenter);
       final DashboardController dashboardController = new DashboardController(dashboardInteractor);
       dashboardView.setDashboardController(dashboardController);
       navigation.registerRefresh("Dashboard", () -> dashboardController.loadDashboard("Cherry"));
       return this;
    }

    public AppBuilder addProfileUseCase() {
        final ProfileOutputBoundary profilePresenter = new ProfilePresenter(profileViewModel, viewManagerModel);
        final ProfileInputBoundary profileInteractor = new ProfileInteractor(userDataAccess, profilePresenter);
        final ProfileController profileController = new ProfileController(profileInteractor);
        profileView.setProfileController(profileController);
        navigation.registerRefresh("Profile", () -> profileController.loadProfile("Cherry"));
        return this;
    }

    public AppBuilder addAddFriendUseCase() {
        final AddFriendOutputBoundary addFriendPresenter = new AddFriendPresenter(addFriendViewModel);
        final AddFriendInputBoundary addFriendInteractor = new AddFriendInteractor(userDataAccess, addFriendPresenter);
        final AddFriendController addFriendController = new AddFriendController(addFriendInteractor);
        addFriendView.setAddFriendController(addFriendController);
        return this;
    }

    public AppBuilder addLogMealsUseCase() {
        // Load API key
        String apiKey = loadApiKey();
        if (apiKey == null) {
            System.err.println("Warning: Failed to load API key for CalorieNinjas. Log Meals feature may not work properly.");
        }

        // Create components
        final LogMealsViewModel logMealsViewModel = new LogMealsViewModel();
        final LogMealsOutputBoundary logMealsPresenter = new LogMealsPresenter(logMealsViewModel);
        final CalorieNinjasApiClient nutritionApi = new CalorieNinjasApiClient(apiKey);
        final LogMealsInputBoundary logMealsInteractor = new LogMealsInteractor(
            mealDataAccess,
            nutritionApi,
            userDataAccess,
            logMealsPresenter
        );
        final LogMealsController logMealsController = new LogMealsController(logMealsInteractor);

        // Create the view
        this.logMealsView = new LogMealsView(
            logMealsViewModel,
            logMealsController,
            mealDataAccess,
            user.getUsername()
        );

        // Connect to dashboard
        dashboardView.setLogMealsView(logMealsView);

        return this;
    }

    private String loadApiKey() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("api.properties")) {
            if (input == null) {
                return null;
            }
            properties.load(input);
            return properties.getProperty("calorie.ninjas.api.key");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JFrame build() {
        final JFrame application = new JFrame("Dashboard");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        navigation.goTo("Login");

        return application;
    }
}
