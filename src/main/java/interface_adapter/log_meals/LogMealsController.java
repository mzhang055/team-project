package interface_adapter.log_meals;

import entities.Meal;
import entities.MealType;
import use_case.log_meals.LogMealsInputBoundary;
import use_case.log_meals.LogMealsInputData;

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
     * Fetches nutritional information for a food without saving it.
     *
     * @param foodName the name of the food entered by the user
     * @param mealType the type of meal (breakfast, lunch, dinner, snack)
     * @param userId the ID of the user
     */
    public void fetchNutrition(String foodName, MealType mealType, String userId) {
        LogMealsInputData inputData = new LogMealsInputData(foodName, mealType, userId);
        interactor.fetchNutrition(inputData);
    }

    /**
     * Saves a meal to the database.
     *
     * @param meal the meal to save
     * @param userId the ID of the user
     */
    public void saveMeal(Meal meal, String userId) {
        interactor.saveMeal(meal, userId);
    }
}
