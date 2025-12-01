package app;

import data_access.InMemoryMealDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import data_access.InMemoryRecipeDataAccessObject;
import data_access.MealDataAccessInterface;
import data_access.UserDataAccessInterface;
import data_access.RecipeDataAccessInterface;
import data_access.TheMealDBRecipeDataAccessObject;
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
import interface_adapter.recipe.RecipeSavedController;
import interface_adapter.recipe.RecipeSavedPresenter;
import interface_adapter.recipe.RecipeSavedViewModel;
import interface_adapter.recipe.RecipeSearchController;
import interface_adapter.recipe.RecipeSearchPresenter;
import interface_adapter.recipe.RecipeSearchViewModel;
import interface_adapter.recipe.SaveRecipeController;
import interface_adapter.recipe.SaveRecipePresenter;
import interface_adapter.update_profile.UpdateProfileController;
import interface_adapter.update_profile.UpdateProfilePresenter;
import interface_adapter.update_profile.UpdateProfileViewModel;
import interface_adapter.view_leaderboard.LeaderboardController;
import interface_adapter.view_leaderboard.LeaderboardPresenter;
import interface_adapter.view_leaderboard.LeaderboardViewmodel;
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
import use_case.profile.ProfileInputBoundary;
import use_case.profile.ProfileInteractor;
import use_case.profile.ProfileOutputBoundary;
import use_case.delete_recipe.*;
import use_case.save_recipe.*;
import use_case.recipe_search.*;
import use_case.saved_recipe.*;
import use_case.update_profile.UpdateProfileInputBoundary;
import use_case.update_profile.UpdateProfileInteractor;
import use_case.update_profile.UpdateProfileOutputBoundary;
import use_case.view_leaderboard.LeaderboardDataAccess;
import use_case.view_leaderboard.LeaderboardDataAccessInterface;
import use_case.view_leaderboard.LeaderboardInteractor;
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
    private LeaderboardView leaderboardView;
    private LeaderboardViewmodel leaderboardViewModel;
    private DashboardView dashboardView;
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
    private DashboardViewModel dashboardViewModel;
    private ProfileView profileView;
    private ProfileViewModel profileViewModel;
    private UpdateProfileView updateProfileView;
    private UpdateProfileViewModel updateProfileViewModel;
    private RecipeMenuView recipeMenuView;
    private LogMealsView logMealsView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        cardPanel.add(legacy, "Legacy");
        userDataAccess.save(user);
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

    public AppBuilder addUpdateProfileView(){
        this.updateProfileViewModel = new UpdateProfileViewModel();
        this.updateProfileView = new UpdateProfileView(updateProfileViewModel, navigation);
        cardPanel.add(updateProfileView, updateProfileView.getViewName());
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

    private DashboardController dashboardController;

    public AppBuilder addDashboardUseCase() {
       final DashboardOutputBoundary dashboardPresenter = new DashboardPresenter(dashboardViewModel, viewManagerModel);
       final DashboardInputBoundary dashboardInteractor = new DashboardInteractor(userDataAccess,mealDataAccess, dashboardPresenter);
       this.dashboardController = new DashboardController(dashboardInteractor);
       dashboardView.setDashboardController(dashboardController);
       navigation.registerRefresh("Dashboard", () -> {
           String currentUser = sessionManager.getCurrentUsername();
           if (currentUser != null) {
               dashboardController.loadDashboard(currentUser);
           }
       });
       return this;
    }

    public AppBuilder addProfileUseCase() {
        final ProfileOutputBoundary profilePresenter = new ProfilePresenter(profileViewModel, viewManagerModel);
        final ProfileInputBoundary profileInteractor = new ProfileInteractor(userDataAccess, profilePresenter);
        final ProfileController profileController = new ProfileController(profileInteractor);
        profileView.setProfileController(profileController);
        navigation.registerRefresh("Profile", () -> {
            String currentUser = sessionManager.getCurrentUsername();
            if (currentUser != null) {
                profileController.loadProfile(currentUser);
            }
        });
        return this;
    }

    public AppBuilder addUpdateProfileUseCase(){
        final UpdateProfileOutputBoundary updateProfilePresenter = new UpdateProfilePresenter(updateProfileViewModel);
        final UpdateProfileInputBoundary updateProfileInteractor = new UpdateProfileInteractor(userDataAccess, updateProfilePresenter);
        final UpdateProfileController updateProfileController = new UpdateProfileController(updateProfileInteractor, sessionManager);
        updateProfileView.setProfileController(updateProfileController);
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
        final LogMealsPresenter logMealsPresenter = new LogMealsPresenter(logMealsViewModel, sessionManager);

        // Connect the dashboard controller to the presenter so it can refresh the dashboard
        if (dashboardController != null) {
            logMealsPresenter.setDashboardController(dashboardController);
        }

        final CalorieNinjasApiClient nutritionApi = new CalorieNinjasApiClient(apiKey);
        final LogMealsInputBoundary logMealsInteractor = new LogMealsInteractor(
            mealDataAccess,
            nutritionApi,
            userDataAccess,
            logMealsPresenter
        );
        final LogMealsController logMealsController = new LogMealsController(logMealsInteractor);

        // Create the view (pass sessionManager instead of hardcoded user)
        this.logMealsView = new LogMealsView(
            logMealsViewModel,
            logMealsController,
            mealDataAccess,
            sessionManager
        );

        // Pass dashboard controller to LogMealsView so it can pass it to LoggedMealsView
        if (dashboardController != null) {
            logMealsView.setDashboardController(dashboardController);
        }

        // Connect to dashboard
        dashboardView.setLogMealsView(logMealsView);

        return this;
    }

    public AppBuilder addRecipeUseCase() {
        navigation.registerRefresh("Dashboard", () -> {
            String currentUser = sessionManager.getCurrentUsername();
            if (currentUser != null && recipeMenuView == null) {
                try {
                    RecipeSearchDataAccessInterface searchDAO =
                            new TheMealDBRecipeDataAccessObject();
                    RecipeDataAccessInterface recipeDataAccess =
                            new InMemoryRecipeDataAccessObject();

                    // ViewModels
                    RecipeSearchViewModel searchVM = new RecipeSearchViewModel();
                    RecipeSavedViewModel savedVM  = new RecipeSavedViewModel();

                    // Presenters
                    SearchRecipeOutputBoundary searchPresenter =
                            new RecipeSearchPresenter(searchVM);
                    SaveRecipeOutputBoundary savePresenter =
                            new SaveRecipePresenter(searchVM);
                    GetSavedRecipesOutputBoundary getSavedPresenter =
                            new RecipeSavedPresenter(savedVM);
                    DeleteSavedRecipeOutputBoundary deletePresenter =
                            new RecipeSavedPresenter(savedVM);

                    // Use cases
                    SearchRecipeInputBoundary searchInteractor =
                            new SearchRecipeInteractor(searchDAO, searchPresenter);

                    SaveRecipeInputBoundary saveInteractor =
                            new SaveRecipeInteractor(userDataAccess, recipeDataAccess, savePresenter);

                    GetSavedRecipesInputBoundary getSavedInteractor =
                            new GetSavedRecipesInteractor(userDataAccess, searchDAO, getSavedPresenter);

                    DeleteSavedRecipeInputBoundary deleteSavedInteractor =
                            new DeleteSavedRecipeInteractor(userDataAccess, deletePresenter);

                    // Controllers
                    RecipeSearchController searchController =
                            new RecipeSearchController(searchInteractor);
                    SaveRecipeController saveController =
                            new SaveRecipeController(saveInteractor);
                    RecipeSavedController savedController =
                            new RecipeSavedController(getSavedInteractor, deleteSavedInteractor);

                    recipeMenuView = new RecipeMenuView(
                            searchController,
                            saveController,
                            searchVM,
                            savedController,
                            savedVM,
                            currentUser
                    );

                    dashboardView.setRecipeMenuView(recipeMenuView);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return this;
    }

    public AppBuilder addLeaderboardView(){
        this.leaderboardViewModel = new LeaderboardViewmodel();

        LeaderboardPresenter leaderboardPresenter = new LeaderboardPresenter(leaderboardViewModel);
        LeaderboardDataAccessInterface leaderboardDataAccess = new LeaderboardDataAccess();
        LeaderboardInteractor leaderboardInteractor = new LeaderboardInteractor(leaderboardPresenter, leaderboardDataAccess);
        LeaderboardController leaderboardController = new LeaderboardController(leaderboardInteractor);

        String currentUsername = sessionManager.getCurrentUsername();
        if (currentUsername == null) {
            currentUsername = "defaultUser";
        }
        this.leaderboardView = new LeaderboardView(null, currentUsername);
        this.leaderboardView.setNavigation(navigation);
        cardPanel.add(leaderboardView, leaderboardView.getViewname());

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
        final JFrame application = new JFrame("App");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        // For development/testing: auto-login as Cherry
        // Comment this out if you want to test the login flow
        sessionManager.setCurrentUsername(user.getUsername());

        navigation.goTo("Login");

        return application;
    }
}
