package use_case.recipe_search;

public class SearchRecipeInputData {
    private final String keyword;

    public SearchRecipeInputData(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}