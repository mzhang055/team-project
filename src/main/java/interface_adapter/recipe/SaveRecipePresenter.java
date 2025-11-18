package interface_adapter.recipe;

import use_case.recipe_log.SaveRecipeOutputBoundary;
import use_case.recipe_log.SaveRecipeOutputData;

public class SaveRecipePresenter implements SaveRecipeOutputBoundary {

    private final RecipeSearchViewModel viewModel;

    public SaveRecipePresenter(RecipeSearchViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(SaveRecipeOutputData outputData) {
        viewModel.setLastSaveMessage(outputData.getMessage());
    }
}
