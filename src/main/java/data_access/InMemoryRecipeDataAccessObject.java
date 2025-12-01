package data_access;

import entities.Recipe;

import java.util.*;

public class InMemoryRecipeDataAccessObject implements RecipeDataAccessInterface {

    private final Map<String, Recipe> recipes = new HashMap<>();
    private final Map<String, List<String>> userRecipes = new HashMap<>();

    @Override
    public synchronized boolean save(String username, Recipe recipe) {
        if (username == null || username.isBlank()) {
            return false;
        }
        if (recipe == null || recipe.getId() == null || recipe.getId().isBlank()) {
            return false;
        }

        recipes.put(recipe.getId(), recipe);

        userRecipes
                .computeIfAbsent(username, k -> new ArrayList<>());
        List<String> ids = userRecipes.get(username);
        if (!ids.contains(recipe.getId())) {
            ids.add(recipe.getId());
        }

        return true;
    }

    @Override
    public synchronized List<Recipe> getRecipesByUser(String username) {
        if (username == null) {
            return Collections.emptyList();
        }
        List<String> ids = userRecipes
                .getOrDefault(username, Collections.emptyList());

        List<Recipe> result = new ArrayList<>();
        for (String id : ids) {
            Recipe recipe = recipes.get(id);
            if (recipe != null) {
                result.add(recipe);
            }
        }
        return result;
    }

    @Override
    public synchronized Optional<Recipe> getRecipeById(String recipeId) {
        if (recipeId == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(recipes.get(recipeId));
    }

    @Override
    public synchronized boolean delete(String username, String recipeId) {
        if (username == null || recipeId == null) {
            return false;
        }

        List<String> ids = userRecipes.get(username);
        if (ids != null) {
            boolean removed = ids.remove(recipeId);
            if (ids.isEmpty()) {
                userRecipes.remove(username);
            }
            return removed;
        }
        return false;
    }

    @Override
    public synchronized boolean hasSavedRecipes(String username) {
        if (username == null) {
            return false;
        }
        List<String> ids = userRecipes.get(username);
        return ids != null && !ids.isEmpty();
    }
}