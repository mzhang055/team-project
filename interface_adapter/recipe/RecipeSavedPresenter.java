package interface_adapter.recipe;

import use_case.delete_recipe.DeleteSavedRecipeOutputBoundary;
import use_case.delete_recipe.DeleteSavedRecipeOutputData;
import use_case.saved_recipe.GetSavedRecipesOutputBoundary;
import use_case.saved_recipe.GetSavedRecipesOutputData;

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