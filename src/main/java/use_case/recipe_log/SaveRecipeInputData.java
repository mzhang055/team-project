package use_case.recipe_log;

import entities.Recipe;

public class SaveRecipeInputData {

    private final String username;
    private final Recipe recipe;

    public SaveRecipeInputData(String username, Recipe recipe) {
        this.username = username;
        this.recipe = recipe;
    }

    public String getUsername() {
        return username;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
