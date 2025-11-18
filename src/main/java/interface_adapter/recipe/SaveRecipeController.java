package interface_adapter.recipe;

import entities.Recipe;
import use_case.recipe_log.SaveRecipeInputBoundary;
import use_case.recipe_log.SaveRecipeInputData;

public class SaveRecipeController {

    private final SaveRecipeInputBoundary saveUseCase;

    public SaveRecipeController(SaveRecipeInputBoundary saveUseCase) {
        this.saveUseCase = saveUseCase;
    }

    public void save(Recipe recipe) {
        SaveRecipeInputData inputData = new SaveRecipeInputData(recipe);
        saveUseCase.execute(inputData);
    }
}