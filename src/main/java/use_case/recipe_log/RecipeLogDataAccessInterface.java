package use_case.recipe_log;

import entities.Recipe;

import java.io.IOException;
import java.util.List;

public interface RecipeLogDataAccessInterface {

    void saveRecipe(Recipe recipe) throws IOException;

    List<Recipe> getAllSavedRecipes() throws IOException;
}
