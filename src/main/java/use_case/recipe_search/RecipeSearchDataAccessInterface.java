package use_case.recipe_search;

import entities.Recipe;

import java.io.IOException;
import java.util.List;

public interface RecipeSearchDataAccessInterface {
    List<Recipe> searchRecipesByName(String name) throws IOException;
}
