package use_case.recipe_log;

import entities.Recipe;

import java.util.List;

public class GetSavedRecipesOutputData {

    private final boolean success;
    private final String message;
    private final List<Recipe> recipes;

    public GetSavedRecipesOutputData(boolean success, String message, List<Recipe> recipes) {
        this.success = success;
        this.message = message;
        this.recipes = recipes;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
