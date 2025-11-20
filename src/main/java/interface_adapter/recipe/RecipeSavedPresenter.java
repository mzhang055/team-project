package interface_adapter.recipe;

import use_case.recipe_log.DeleteSavedRecipeOutputBoundary;
import use_case.recipe_log.DeleteSavedRecipeOutputData;
import use_case.recipe_log.GetSavedRecipesOutputBoundary;
import use_case.recipe_log.GetSavedRecipesOutputData;

public class RecipeSavedPresenter implements
        GetSavedRecipesOutputBoundary,
        DeleteSavedRecipeOutputBoundary {

    private final RecipeSavedViewModel viewModel;

    public RecipeSavedPresenter(RecipeSavedViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(GetSavedRecipesOutputData outputData) {
        if (outputData.isSuccess()) {
            viewModel.setErrorMessage("");
            viewModel.setRecipes(outputData.getRecipes());
        } else {
            viewModel.setErrorMessage(outputData.getMessage());
            viewModel.setRecipes(null);
        }
    }

    @Override
    public void present(DeleteSavedRecipeOutputData outputData) {
        viewModel.setLastDeleteMessage(outputData.getMessage());
    }
}