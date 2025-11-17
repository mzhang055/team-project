package use_case.LogMeals;

import entities.Meal;

/**
 * Output data for the Log Meals use case.
 * Contains the result of attempting to log a meal.
 */
public class LogMealsOutputData {
    private final Meal meal;
    private final boolean success;

    /**
     * Constructs output data for a successful meal logging.
     *
     * @param meal the meal that was logged
     * @param success whether the operation was successful
     */
    public LogMealsOutputData(Meal meal, boolean success) {
        this.meal = meal;
        this.success = success;
    }

    public Meal getMeal() {
        return meal;
    }

    public boolean isSuccess() {
        return success;
    }
}
