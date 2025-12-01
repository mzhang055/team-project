package use_case.delete_recipe;

import entities.User;
import data_access.UserDataAccessInterface;

public class DeleteSavedRecipeInteractor implements DeleteSavedRecipeInputBoundary {

    private final UserDataAccessInterface userDAO;
    private final DeleteSavedRecipeOutputBoundary presenter;

    public DeleteSavedRecipeInteractor(UserDataAccessInterface userDAO,
                                       DeleteSavedRecipeOutputBoundary presenter) {
        this.userDAO = userDAO;
        this.presenter = presenter;
    }

    @Override
    public void execute(DeleteSavedRecipeInputData inputData) {
        String username = inputData.getUsername();
        String recipeId = inputData.getRecipeId();

        if (username == null || username.isBlank()) {
            presenter.present(new DeleteSavedRecipeOutputData(false, "No user logged in."));
            return;
        }
        if (recipeId == null || recipeId.isBlank()) {
            presenter.present(new DeleteSavedRecipeOutputData(false, "No recipe selected."));
            return;
        }

        if (!userDAO.existsByUsername(username)) {
            presenter.present(new DeleteSavedRecipeOutputData(false, "User not found."));
            return;
        }

        User user = userDAO.getUser(username);
        if (user == null) {
            presenter.present(new DeleteSavedRecipeOutputData(false, "User not found."));
            return;
        }

        if (!user.getRecipeIds().contains(recipeId)) {
            presenter.present(new DeleteSavedRecipeOutputData(false, "Recipe is not saved."));
            return;
        }

        user.removeRecipe(recipeId);
        userDAO.save(user);

        presenter.present(new DeleteSavedRecipeOutputData(true, "Recipe deleted."));
    }
}
