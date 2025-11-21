package use_case.recipe_log;

import data_access.UserDataAccessInterface;
import entities.Recipe;
import entities.User;

public class SaveRecipeInteractor implements SaveRecipeInputBoundary {

    private final UserDataAccessInterface userDAO;
    private final SaveRecipeOutputBoundary presenter;

    public SaveRecipeInteractor(UserDataAccessInterface userDAO,
                                SaveRecipeOutputBoundary presenter) {
        this.userDAO = userDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(SaveRecipeInputData inputData) {
        String username = inputData.getUsername();
        Recipe recipe = inputData.getRecipe();

        if (username == null || username.isBlank()) {
            presenter.present(new SaveRecipeOutputData(false, "No user logged in."));
            return;
        }
        if (recipe == null || recipe.getId() == null || recipe.getId().isBlank()) {
            presenter.present(new SaveRecipeOutputData(false, "No recipe to save."));
            return;
        }

        if (!userDAO.existsByUsername(username)) {
            presenter.present(new SaveRecipeOutputData(false, "User not found."));
            return;
        }

        User user = userDAO.getUser(username);
        if (user == null) {
            presenter.present(new SaveRecipeOutputData(false, "User not found."));
            return;
        }

        user.addRecipe(recipe.getId());
        userDAO.save(user);

        presenter.present(new SaveRecipeOutputData(true, "Recipe saved for user."));
    }
}