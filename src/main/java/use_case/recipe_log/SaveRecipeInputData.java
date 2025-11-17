package use_case.recipe_log;

import entities.Recipe;

public class SaveRecipeInputData {
    private final Recipe recipe;

    public SaveRecipeInputData(Recipe recipe) {
        this.recipe = recipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
