package interface_adapter.recipe;

import entities.Recipe;
import use_case.save_recipe.SaveRecipeInputBoundary;
import use_case.save_recipe.SaveRecipeInputData;

public class SaveRecipeController {

    private final SaveRecipeInputBoundary saveUseCase;

    public SaveRecipeController(SaveRecipeInputBoundary saveUseCase) {
        this.saveUseCase = saveUseCase;
    }

    public void save(String username, Recipe recipe) {
        SaveRecipeInputData inputData = new SaveRecipeInputData(username, recipe);
        saveUseCase.execute(inputData);
    }
}