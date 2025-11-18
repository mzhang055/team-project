package interface_adapter.recipe;

import use_case.recipe_log.GetSavedRecipesInputBoundary;

public class RecipeSavedController {

    private final GetSavedRecipesInputBoundary getSavedUseCase;

    public RecipeSavedController(GetSavedRecipesInputBoundary getSavedUseCase) {
        this.getSavedUseCase = getSavedUseCase;
    }

    public void refresh() {
        getSavedUseCase.execute();
    }
}
