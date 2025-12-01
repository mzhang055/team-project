package interface_adapter.recipe;

import use_case.delete_recipe.DeleteSavedRecipeInputBoundary;
import use_case.delete_recipe.DeleteSavedRecipeInputData;
import use_case.saved_recipe.GetSavedRecipesInputBoundary;
import use_case.saved_recipe.GetSavedRecipesInputData;

public class RecipeSavedController {

    private final GetSavedRecipesInputBoundary getSavedUseCase;
    private final DeleteSavedRecipeInputBoundary deleteUseCase;

    public RecipeSavedController(GetSavedRecipesInputBoundary getSavedUseCase,
                                 DeleteSavedRecipeInputBoundary deleteUseCase) {
        this.getSavedUseCase = getSavedUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    public void refresh(String username) {
        GetSavedRecipesInputData inputData = new GetSavedRecipesInputData(username);
        getSavedUseCase.execute(inputData);
    }

    public void delete(String username, String recipeId) {
        DeleteSavedRecipeInputData inputData =
                new DeleteSavedRecipeInputData(username, recipeId);
        deleteUseCase.execute(inputData);
    }
}
