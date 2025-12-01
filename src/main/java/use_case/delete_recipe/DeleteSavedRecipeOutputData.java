package use_case.delete_recipe;

public class DeleteSavedRecipeOutputData {

    private final boolean success;
    private final String message;

    public DeleteSavedRecipeOutputData(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
