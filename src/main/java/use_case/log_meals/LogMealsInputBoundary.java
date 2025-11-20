package use_case.log_meals;

/**
 * Input boundary interface for the Log Meals use case.
 * Defines the operations that can be performed for logging meals.
 */
public interface LogMealsInputBoundary {

    /**
     * Logs a meal based on the provided input data.
     *
     * @param inputData the input data containing food name, meal type, and user ID
     */
    void logMeal(LogMealsInputData inputData);
}
