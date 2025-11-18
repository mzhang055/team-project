package interface_adapter.recipe;

import use_case.recipe_log.GetSavedRecipesOutputBoundary;
import use_case.recipe_log.GetSavedRecipesOutputData;

public class RecipeSavedPresenter implements GetSavedRecipesOutputBoundary {

    private final RecipeSavedViewModel viewModel;

    public RecipeSavedPresenter(RecipeSavedViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(GetSavedRecipesOutputData outputData) {
        if (outputData.isSuccess()) {
            viewModel.setRecipes(outputData.getRecipes());
            viewModel.setErrorMessage("");
        } else {
            viewModel.setRecipes(outputData.getRecipes());
            viewModel.setErrorMessage(outputData.getMessage());
        }
    }
}