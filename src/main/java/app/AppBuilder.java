package app;

import data_access.InMemoryMealDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import data_access.MealDataAccessInterface;
import data_access.UserDataAccessInterface;
import entities.User;
import entities.UserFactory;
import interface_adapter.Navigation;
import interface_adapter.ViewManagerModel;
import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardPresenter;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.log_meals.CalorieNinjasApiClient;
import interface_adapter.log_meals.LogMealsController;
import interface_adapter.log_meals.LogMealsPresenter;
import interface_adapter.log_meals.LogMealsViewModel;
import interface_adapter.profile.ProfileController;
import interface_adapter.profile.ProfilePresenter;
import interface_adapter.profile.ProfileViewModel;
import use_case.dashboard.DashboardInputBoundary;
import use_case.dashboard.DashboardInteractor;
import use_case.dashboard.DashboardOutputBoundary;
import use_case.log_meals.LogMealsInputBoundary;
import use_case.log_meals.LogMealsInteractor;
import use_case.log_meals.LogMealsOutputBoundary;
import use_case.profile.ProfileInputBoundary;
import use_case.profile.ProfileInteractor;
import use_case.profile.ProfileOutputBoundary;
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
    private final CardLayout cardLayout = new CardLayout();
    final UserFactory userFactory = new UserFactory();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    InMemoryUserDataAccessObject userDataAccess = new InMemoryUserDataAccessObject();
    InMemoryMealDataAccessObject mealDataAccess = new InMemoryMealDataAccessObject();
    Navigation navigation = new Navigation(viewManagerModel);

    User user = new User("Cherry", "Red");

    private DashboardView dashboardView;
    private DashboardViewModel dashboardViewModel;
    private ProfileView profileView;
    private ProfileViewModel profileViewModel;
    private RecipeMenuView recipeMenuView;
    private LogMealsView logMealsView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        userDataAccess.save(user);

        RecipeModuleBuilder recipeBuilder = new RecipeModuleBuilder(userDataAccess);
        this.recipeMenuView = recipeBuilder.buildRecipeMenuView(user.getUsername());
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

        viewManagerModel.setState(dashboardView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }
}
