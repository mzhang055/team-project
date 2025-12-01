package data_access;

import entities.Recipe;

import java.util.List;
import java.util.Optional;


public interface RecipeDataAccessInterface {

    boolean save(String username, Recipe recipe);

    List<Recipe> getRecipesByUser(String username);
    Optional<Recipe> getRecipeById(String recipeId);

    boolean delete(String username, String recipeId);
    boolean hasSavedRecipes(String username);
}
