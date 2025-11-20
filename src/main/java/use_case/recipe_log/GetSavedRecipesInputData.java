package use_case.recipe_log;

public class GetSavedRecipesInputData {

    private final String username;

    public GetSavedRecipesInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}