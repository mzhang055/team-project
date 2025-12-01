package use_case.save_recipe;

import data_access.RecipeDataAccessInterface;
import data_access.UserDataAccessInterface;
import entities.Recipe;
import entities.User;

public class SaveRecipeInteractor implements SaveRecipeInputBoundary {

    private final UserDataAccessInterface userDataAccess;
    private final RecipeDataAccessInterface recipeDataAccess;
    private final SaveRecipeOutputBoundary presenter;

    public SaveRecipeInteractor(UserDataAccessInterface userDataAccess,
                                RecipeDataAccessInterface recipeDataAccess,
                                SaveRecipeOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.recipeDataAccess = recipeDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(SaveRecipeInputData inputData) {
        String username = inputData.getUsername();
        Recipe recipe = inputData.getRecipe();

        if (username == null || username.isEmpty()) {
            presenter.present(new SaveRecipeOutputData(
                    false,
                    "No username provided."
            ));
            return;
        }
        if (recipe == null) {
            presenter.present(new SaveRecipeOutputData(
                    false,
                    "No recipe selected."
            ));
            return;
        }
        if (recipe.getId() == null || recipe.getId().isEmpty()) {
            presenter.present(new SaveRecipeOutputData(
                    false,
                    "Recipe has no id and cannot be saved."
            ));
            return;
        }

        if (!userDataAccess.existsByUsername(username)) {
            presenter.present(new SaveRecipeOutputData(
                    false,
                    "User not found: " + username
            ));
            return;
        }

        User user = userDataAccess.getUser(username);
        user.addRecipe(recipe.getId());
        userDataAccess.save(user);

        boolean ok = recipeDataAccess.save(username, recipe);
        if (!ok) {
            presenter.present(new SaveRecipeOutputData(
                    false,
                    "Recipe saved for user, but failed to save into local recipe DB."
            ));
        } else {
            presenter.present(new SaveRecipeOutputData(
                    true,
                    "Saved recipe: " + recipe.getName()
            ));
        }
    }
}