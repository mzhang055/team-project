package use_case.LogMeals;

import entities.Meal;
import entities.NutritionalInfo;
import entities.User;
import use_case.shared.UserDataAccessInterface;
import java.util.Optional;

/**
 * Interactor for the Log Meals use case.
 * Logic for logging meals manually
 */
public class LogMealsInteractor implements LogMealsInputBoundary {

    private final MealDataAccessInterface mealDataAccess;
    private final NutritionApiInterface nutritionApi;
    private final UserDataAccessInterface userDataAccess;
    private final LogMealsOutputBoundary outputBoundary;

    /**
     * Constructs a LogMealsInteractor
     *
     * @param mealDataAccess the data access interface for storing/retrieving meals
     * @param nutritionApi the API interface for fetching nutritional information
     * @param userDataAccess the data access interface for user operations
     * @param outputBoundary the output boundary for presenting results
     */
    public LogMealsInteractor(MealDataAccessInterface mealDataAccess,
                              NutritionApiInterface nutritionApi,
                              UserDataAccessInterface userDataAccess,
                              LogMealsOutputBoundary outputBoundary) {
        this.mealDataAccess = mealDataAccess;
        this.nutritionApi = nutritionApi;
        this.userDataAccess = userDataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void logMeal(LogMealsInputData inputData) {
        // Validate food name
        if (inputData.getFoodName() == null || inputData.getFoodName().trim().isEmpty()) {
            outputBoundary.prepareFailView("Food name cannot be empty");
            return;
        }

        // Fetch nutritional information from API
        Optional<NutritionalInfo> nutritionInfoOptional = nutritionApi.getNutritionalInfo(inputData.getFoodName());

        if (nutritionInfoOptional.isEmpty()) {
            outputBoundary.prepareFailView("Could not fetch nutritional information for: " + inputData.getFoodName());
            return;
        }

        NutritionalInfo nutritionInfo = nutritionInfoOptional.get();

        // Create meal entity
        Meal meal = Meal.builder(inputData.getFoodName())
                .mealType(inputData.getMealType())
                .userId(inputData.getUserId())
                .nutritionalInfo(nutritionInfo)
                .build();

        // Save meal to data store
        boolean saved = mealDataAccess.save(meal);

        if (!saved) {
            outputBoundary.prepareFailView("Failed to save meal");
            return;
        }

        // Update user's meal list
        User user = userDataAccess.getUser(inputData.getUserId());
        if (user != null) {
            user.addMeal(meal.getId());
            userDataAccess.save(user);
        }

        // Prepare success view
        LogMealsOutputData outputData = new LogMealsOutputData(meal, true);
        outputBoundary.prepareSuccessView(outputData);
    }
}
