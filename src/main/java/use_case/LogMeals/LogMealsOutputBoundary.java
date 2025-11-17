package use_case.LogMeals;

/**
 * Output boundary interface for the Log Meals use case.
 * Defines how results are presented back to the user.
 */
public interface LogMealsOutputBoundary {

    /**
     * Prepares the view for a successful meal logging operation.
     *
     * @param outputData the output data containing the logged meal
     */
    void prepareSuccessView(LogMealsOutputData outputData);

    /**
     * Prepares the view for a failed meal logging operation.
     *
     * @param error the error message describing what went wrong
     */
    void prepareFailView(String error);
}
