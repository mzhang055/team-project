package interface_adapter.LogMeals;

import use_case.LogMeals.LogMealsOutputBoundary;
import use_case.LogMeals.LogMealsOutputData;

/**
 * Presenter for the Log Meals use case.
 * Formats the output data for display to the user.
 */
public class LogMealsPresenter implements LogMealsOutputBoundary {

    private LogMealsViewModel viewModel;

    /**
     * Constructs a LogMealsPresenter with the given view model.
     *
     * @param viewModel the view model to update
     */
    public LogMealsPresenter(LogMealsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(LogMealsOutputData outputData) {
        // Format the meal data for display
        String message = "Meal logged successfully!\n" +
                "Food: " + outputData.getMeal().getFoodName() + "\n" +
                "Type: " + outputData.getMeal().getMealType() + "\n" +
                outputData.getMeal().getNutritionalInfo().toString();

        viewModel.setSuccessMessage(message);
        viewModel.setMeal(outputData.getMeal());
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        viewModel.setErrorMessage(error);
        viewModel.firePropertyChanged();
    }
}
