package use_case.recipe_log;

import data_access.UserDataAccessInterface;
import entities.Recipe;
import entities.User;
import use_case.recipe_search.RecipeSearchDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetSavedRecipesInteractor implements GetSavedRecipesInputBoundary {

    private final UserDataAccessInterface userDAO;
    private final RecipeSearchDataAccessInterface recipeSearchDAO;
    private final GetSavedRecipesOutputBoundary presenter;

    public GetSavedRecipesInteractor(UserDataAccessInterface userDAO,
                                     RecipeSearchDataAccessInterface recipeSearchDAO,
                                     GetSavedRecipesOutputBoundary presenter) {
        this.userDAO = userDAO;
        this.recipeSearchDAO = recipeSearchDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(GetSavedRecipesInputData inputData) {
        String username = inputData.getUsername();
        if (username == null || username.isBlank()) {
            presenter.present(new GetSavedRecipesOutputData(
                    false, "No user logged in.", Collections.emptyList()));
            return;
        }

        if (!userDAO.existsByUsername(username)) {
            presenter.present(new GetSavedRecipesOutputData(
                    false, "User not found.", Collections.emptyList()));
            return;
        }

        User user = userDAO.getUser(username);
        if (user == null) {
            presenter.present(new GetSavedRecipesOutputData(
                    false, "User not found.", Collections.emptyList()));
            return;
        }

        List<String> recipeIds = user.getRecipeIds();
        if (recipeIds.isEmpty()) {
            presenter.present(new GetSavedRecipesOutputData(
                    true, "No recipes saved yet.", Collections.emptyList()));
            return;
        }

        List<Recipe> recipes = new ArrayList<>();
        for (String id : recipeIds) {
            if (id == null || id.isBlank()) {
                continue;
            }
            try {
                Recipe recipe = recipeSearchDAO.getRecipeById(id);
                if (recipe != null) {
                    recipes.add(recipe);
                }
            } catch (IOException e) {
            }
        }

        if (recipes.isEmpty()) {
            presenter.present(new GetSavedRecipesOutputData(
                    true, "No valid recipes found.", Collections.emptyList()));
        } else {
            presenter.present(new GetSavedRecipesOutputData(
                    true, "Loaded recipes.", recipes));
        }
    }
}