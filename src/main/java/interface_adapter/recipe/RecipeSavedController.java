package interface_adapter.recipe;

import use_case.recipe_log.DeleteSavedRecipeInputBoundary;
import use_case.recipe_log.DeleteSavedRecipeInputData;
import use_case.recipe_log.GetSavedRecipesInputBoundary;
import use_case.recipe_log.GetSavedRecipesInputData;

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
