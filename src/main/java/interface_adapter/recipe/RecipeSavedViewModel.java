package interface_adapter.recipe;

import entities.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeSavedViewModel {

    private List<Recipe> recipes = new ArrayList<>();
    private String errorMessage = "";

    public List<Recipe> getRecipes() {
        return new ArrayList<>(recipes);
    }

    public void setRecipes(List<Recipe> recipes) {
        if (recipes == null) {
            this.recipes = new ArrayList<>();
        } else {
            this.recipes = new ArrayList<>(recipes);
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
