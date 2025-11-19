package interface_adapter.recipe;

import entities.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeSavedViewModel {

    private List<Recipe> recipes = new ArrayList<>();
    private String errorMessage = "";
    private String lastDeleteMessage = "";

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
        return errorMessage == null ? "" : errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage == null ? "" : errorMessage;
    }

    public String getLastDeleteMessage() {
        return lastDeleteMessage == null ? "" : lastDeleteMessage;
    }

    public void setLastDeleteMessage(String lastDeleteMessage) {
        this.lastDeleteMessage = lastDeleteMessage == null ? "" : lastDeleteMessage;
    }
}
