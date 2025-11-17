package interface_adapter.recipe;

import entities.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeSearchViewModel {

    private List<Recipe> recipes = new ArrayList<>();
    private int selectedIndex = -1;

    private String searchResultText = "";
    private boolean canSave = false;
    private String lastSaveMessage = "";

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

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public Recipe getSelectedRecipe() {
        if (selectedIndex < 0 || selectedIndex >= recipes.size()) {
            return null;
        }
        return recipes.get(selectedIndex);
    }

    public String getSearchResultText() {
        return searchResultText;
    }

    public void setSearchResultText(String searchResultText) {
        this.searchResultText = searchResultText;
    }

    public boolean isCanSave() {
        return canSave;
    }

    public void setCanSave(boolean canSave) {
        this.canSave = canSave;
    }

    public String getLastSaveMessage() {
        return lastSaveMessage;
    }

    public void setLastSaveMessage(String lastSaveMessage) {
        this.lastSaveMessage = lastSaveMessage;
    }
}
