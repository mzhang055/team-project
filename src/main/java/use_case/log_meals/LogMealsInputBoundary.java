package use_case.log_meals;

import entities.Meal;

/**
 * Input boundary interface for the Log Meals use case.
 * Defines the operations that can be performed for logging meals.
 */
public interface LogMealsInputBoundary {

    /**
     * Fetches nutritional information for a food without saving it.
     *
     * @param inputData the input data containing food name and meal type
     */
    void fetchNutrition(LogMealsInputData inputData);

    /**
     * Saves a meal to the database.
     *
     * @param meal the meal to save
     * @param userId the user ID
     */
    void saveMeal(Meal meal, String userId);
}
