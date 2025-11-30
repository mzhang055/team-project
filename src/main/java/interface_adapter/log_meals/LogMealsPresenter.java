package interface_adapter.log_meals;

import interface_adapter.SessionManager;
import interface_adapter.dashboard.DashboardController;
import use_case.log_meals.LogMealsOutputBoundary;
import use_case.log_meals.LogMealsOutputData;

/**
 * Presenter for the Log Meals use case.
 * Formats the output data for display to the user.
 */
public class LogMealsPresenter implements LogMealsOutputBoundary {

    private LogMealsViewModel viewModel;
    private DashboardController dashboardController;
    private SessionManager sessionManager;

    /**
     * Constructs a LogMealsPresenter with the given view model.
     *
     * @param viewModel the view model to update
     * @param sessionManager the session manager for storing pending meals
     */
    public LogMealsPresenter(LogMealsViewModel viewModel, SessionManager sessionManager) {
        this.viewModel = viewModel;
        this.sessionManager = sessionManager;
    }

    /**
     * Sets the dashboard controller to refresh the dashboard after logging meals.
     *
     * @param dashboardController the dashboard controller
     */
    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    @Override
    public void prepareSuccessView(LogMealsOutputData outputData) {
        // Format the fetched nutrition data for display (not saved yet)
        String message = "Nutrition data fetched successfully!\n" +
                "Food: " + outputData.getMeal().getFoodName() + "\n" +
                "Type: " + outputData.getMeal().getMealType() + "\n" +
                outputData.getMeal().getNutritionalInfo().toString() + "\n" +
                "Click 'Save Meal' to log this meal.";

        viewModel.setSuccessMessage(message);
        viewModel.firePropertyChanged();

        // Store the meal in the session as a pending meal (not saved to database yet)
        sessionManager.setPendingMeal(outputData.getMeal());
    }

    @Override
    public void prepareSaveSuccessView(LogMealsOutputData outputData) {
        // Format the save confirmation message
        String message = "Meal saved successfully!\n" +
                "Food: " + outputData.getMeal().getFoodName() + "\n" +
                "Type: " + outputData.getMeal().getMealType() + "\n" +
                outputData.getMeal().getNutritionalInfo().toString();

        viewModel.setSuccessMessage(message);
        viewModel.firePropertyChanged();

        // Clear the pending meal from session
        sessionManager.clearPendingMeal();

        // Refresh the dashboard to show updated nutrition totals
        if (dashboardController != null && sessionManager.getCurrentUsername() != null) {
            dashboardController.loadDashboard(sessionManager.getCurrentUsername());
        }
    }

    @Override
    public void prepareFailView(String error) {
        viewModel.setErrorMessage(error);
        viewModel.firePropertyChanged();
    }
}
