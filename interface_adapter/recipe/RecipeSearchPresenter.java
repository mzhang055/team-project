package interface_adapter.recipe;


import entities.Ingredient;
import entities.Recipe;
import use_case.recipe_search.SearchRecipeOutputBoundary;
import use_case.recipe_search.SearchRecipeOutputData;

import java.util.List;

public class RecipeSearchPresenter implements SearchRecipeOutputBoundary {

    private final RecipeSearchViewModel viewModel;

    public RecipeSearchPresenter(RecipeSearchViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(SearchRecipeOutputData outputData) {
        viewModel.setLastSaveMessage("");

        if (!outputData.isSuccess()) {
            viewModel.setRecipes(List.of());
            viewModel.setSelectedIndex(-1);
            viewModel.setSearchResultText(outputData.getMessage());
            viewModel.setCanSave(false);
            return;
        }

        List<Recipe> recipes = outputData.getRecipes();
        viewModel.setRecipes(recipes);

        if (recipes.isEmpty()) {
            viewModel.setSelectedIndex(-1);
            viewModel.setSearchResultText("No recipe matches.");
            viewModel.setCanSave(false);
            return;
        }

        viewModel.setSelectedIndex(0);
        Recipe first = recipes.get(0);
        viewModel.setSearchResultText(formatRecipe(first));
        viewModel.setCanSave(true);
    }

    private String formatRecipe(Recipe r) {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(nullSafe(r.getName())).append("\n");
        sb.append("Category: ").append(nullSafe(r.getCategory())).append("\n");
        sb.append("Area: ").append(nullSafe(r.getArea())).append("\n");
        sb.append("Image: ").append(nullSafe(r.getImageUrl())).append("\n\n");

        sb.append("Ingredients:\n");
        for (Ingredient ing : r.getIngredients()) {
            sb.append(" - ").append(ing).append("\n");
        }

        sb.append("\nInstructions:\n");
        sb.append(nullSafe(r.getInstructions())).append("\n");
        return sb.toString();
    }

    private String nullSafe(String s) {
        return s == null ? "" : s;
    }
}