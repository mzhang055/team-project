package use_case.log_meals;

/**
 * Output boundary interface for the Log Meals use case.
 * Defines how results are presented back to the user.
 */
public interface LogMealsOutputBoundary {

    /**
     * Prepares the view after fetching nutrition data (not saved yet).
     *
     * @param outputData the output data containing the meal with nutrition info
     */
    void prepareSuccessView(LogMealsOutputData outputData);

    /**
     * Prepares the view after successfully saving a meal.
     *
     * @param outputData the output data containing the saved meal
     */
    void prepareSaveSuccessView(LogMealsOutputData outputData);

    /**
     * Prepares the view for a failed meal logging operation.
     *
     * @param error the error message describing what went wrong
     */
    void prepareFailView(String error);
}
