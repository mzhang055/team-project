package use_case.recipe_search;

import entities.Recipe;

import java.io.IOException;
import java.util.List;

public class SearchRecipeInteractor implements SearchRecipeInputBoundary {

    private final RecipeSearchDataAccessInterface recipeSearchDAO;
    private final SearchRecipeOutputBoundary presenter;

    public SearchRecipeInteractor(RecipeSearchDataAccessInterface recipeSearchDAO,
                                  SearchRecipeOutputBoundary presenter) {
        this.recipeSearchDAO = recipeSearchDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(SearchRecipeInputData inputData) {
        String keyword = inputData.getKeyword();

        if (keyword == null || keyword.trim().isEmpty()) {
            presenter.present(new SearchRecipeOutputData(
                    List.of(), "Recipe name cannot be empty.", false));
            return;
        }

        try {
            List<Recipe> recipes = recipeSearchDAO.searchRecipesByName(keyword.trim());
            if (recipes.isEmpty()) {
                presenter.present(new SearchRecipeOutputData(
                        List.of(), "No recipe matches.", false));
            } else {
                presenter.present(new SearchRecipeOutputData(
                        recipes, "Found " + recipes.size() + " recipe(s).", true));
            }
        } catch (IOException e) {
            presenter.present(new SearchRecipeOutputData(
                    List.of(), "Error while calling recipe API: " + e.getMessage(), false));
        }
    }
}