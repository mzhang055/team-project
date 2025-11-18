package interface_adapter.recipe;

import use_case.recipe_search.SearchRecipeInputBoundary;
import use_case.recipe_search.SearchRecipeInputData;

public class RecipeSearchController {

    private final SearchRecipeInputBoundary searchUseCase;

    public RecipeSearchController(SearchRecipeInputBoundary searchUseCase) {
        this.searchUseCase = searchUseCase;
    }

    public void search(String keyword) {
        SearchRecipeInputData inputData = new SearchRecipeInputData(keyword);
        searchUseCase.execute(inputData);
    }
}
