package use_case.recipe_log;

import entities.Recipe;

import java.io.IOException;

public class SaveRecipeInteractor implements SaveRecipeInputBoundary {

    private final RecipeLogDataAccessInterface recipeLogDAO;
    private final SaveRecipeOutputBoundary presenter;

    public SaveRecipeInteractor(RecipeLogDataAccessInterface recipeLogDAO,
                                SaveRecipeOutputBoundary presenter) {
        this.recipeLogDAO = recipeLogDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(SaveRecipeInputData inputData) {
        Recipe recipe = inputData.getRecipe();

        if (recipe == null) {
            presenter.present(new SaveRecipeOutputData(false, "No recipe to save."));
            return;
        }

        try {
            recipeLogDAO.saveRecipe(recipe);
            presenter.present(new SaveRecipeOutputData(true, "Recipe saved."));
        } catch (IOException e) {
            presenter.present(new SaveRecipeOutputData(false,
                    "Failed to save recipe: " + e.getMessage()));
        }
    }
}