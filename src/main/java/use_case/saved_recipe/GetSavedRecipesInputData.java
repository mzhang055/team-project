package use_case.saved_recipe;

public class GetSavedRecipesInputData {

    private final String username;

    public GetSavedRecipesInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}