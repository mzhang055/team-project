package app;

import data_access.InMemoryUserDataAccessObject;
import interface_adapter.LogMeals.*;
import use_case.LogMeals.LogMealsInteractor;
import view.LogMealsView;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Main application for testing the Log Meals feature with Swing UI.
 */
public class LogMealsApp {

    private static final String USER_ID = "test-user";

    public static void main(String[] args) {
        // Load API key from properties file
        String apiKey = loadApiKey();
        if (apiKey == null) {
            System.err.println("Failed to load API key from api.properties");
            return;
        }
        // Create data access implementations
        InMemoryMealDataAccess mealDataAccess = new InMemoryMealDataAccess();
        CalorieNinjasApiClient nutritionApi = new CalorieNinjasApiClient(apiKey);
        InMemoryUserDataAccessObject userDataAccess = new InMemoryUserDataAccessObject();

        // Create view model
        LogMealsViewModel viewModel = new LogMealsViewModel();

        // Create presenter
        LogMealsPresenter presenter = new LogMealsPresenter(viewModel);

        // Create interactor
        LogMealsInteractor interactor = new LogMealsInteractor(
            mealDataAccess,
            nutritionApi,
            userDataAccess,
            presenter
        );

        // Create controller
        LogMealsController controller = new LogMealsController(interactor);

        // Create and show view
        SwingUtilities.invokeLater(() -> {
            LogMealsView view = new LogMealsView(viewModel, controller, mealDataAccess, USER_ID);
            view.setVisible(true);
        });
    }

    private static String loadApiKey() {
        Properties properties = new Properties();
        try (InputStream input = LogMealsApp.class.getClassLoader().getResourceAsStream("api.properties")) {
            if (input == null) {
                System.err.println("Unable to find api.properties");
                return null;
            }
            properties.load(input);
            return properties.getProperty("calorie.ninjas.api.key");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
