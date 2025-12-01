package use_case.saved_recipe;

import entities.Recipe;

import java.util.ArrayList;
import java.util.List;

public class GetSavedRecipesOutputData {

    private final boolean success;
    private final String message;
    private final List<Recipe> recipes;

    public GetSavedRecipesOutputData(boolean success, String message, List<Recipe> recipes) {
        this.success = success;
        this.message = message;
        if (recipes == null) {
            this.recipes = new ArrayList<>();
        } else {
            this.recipes = new ArrayList<>(recipes);
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<Recipe> getRecipes() {
        return new ArrayList<>(recipes);
    }
}