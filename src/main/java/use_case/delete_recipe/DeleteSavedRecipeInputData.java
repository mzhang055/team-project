package use_case.delete_recipe;

public class DeleteSavedRecipeInputData {

    private final String username;
    private final String recipeId;

    public DeleteSavedRecipeInputData(String username, String recipeId) {
        this.username = username;
        this.recipeId = recipeId;
    }

    public String getUsername() {
        return username;
    }

    public String getRecipeId() {
        return recipeId;
    }
}