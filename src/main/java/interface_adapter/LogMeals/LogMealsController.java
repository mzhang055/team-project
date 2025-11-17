package interface_adapter.LogMeals;

import entities.MealType;
import use_case.LogMeals.LogMealsInputBoundary;
import use_case.LogMeals.LogMealsInputData;

/**
 * Controller for the Log Meals use case.
 * Handles user input and delegates to the interactor.
 */
public class LogMealsController {

    private final LogMealsInputBoundary interactor;

    /**
     * Constructs a LogMealsController with the given interactor
     *
     * @param interactor the input boundary for logging meals
     */
    public LogMealsController(LogMealsInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Handles the user's request to log a meal manually
     *
     * @param foodName the name of the food entered by the user
     * @param mealType the type of meal (breakfast, lunch, dinner, snack)
     * @param userId the ID of the user logging the meal
     */
    public void logMeal(String foodName, MealType mealType, String userId) {
        LogMealsInputData inputData = new LogMealsInputData(foodName, mealType, userId);
        interactor.logMeal(inputData);
    }
}
