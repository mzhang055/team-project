package use_case.recipe_log;

import entities.Recipe;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class GetSavedRecipesInteractor implements GetSavedRecipesInputBoundary {

    private final RecipeLogDataAccessInterface recipeLogDAO;
    private final GetSavedRecipesOutputBoundary presenter;

    public GetSavedRecipesInteractor(RecipeLogDataAccessInterface recipeLogDAO,
                                     GetSavedRecipesOutputBoundary presenter) {
        this.recipeLogDAO = recipeLogDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        try {
            List<Recipe> recipes = recipeLogDAO.getAllSavedRecipes();
            presenter.present(new GetSavedRecipesOutputData(
                    true, "Loaded recipes.", recipes));
        } catch (IOException e) {
            presenter.present(new GetSavedRecipesOutputData(
                    false, "Failed to load saved recipes: " + e.getMessage(),
                    Collections.emptyList()));
        }
    }
}
