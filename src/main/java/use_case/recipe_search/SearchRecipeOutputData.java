package use_case.recipe_search;

import entities.Recipe;

import java.util.ArrayList;
import java.util.List;

public class SearchRecipeOutputData {

    private final List<Recipe> recipes;
    private final String message;
    private final boolean success;

    public SearchRecipeOutputData(List<Recipe> recipes, String message, boolean success) {
        if (recipes == null) {
            this.recipes = new ArrayList<>();
        } else {
            this.recipes = new ArrayList<>(recipes);
        }
        this.message = message;
        this.success = success;
    }

    public List<Recipe> getRecipes() {
        return new ArrayList<>(recipes);
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
