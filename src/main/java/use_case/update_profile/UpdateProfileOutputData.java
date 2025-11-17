package use_case.update_profile;

public class UpdateProfileOutputData {
    private final String username;
    private final String message;

    public UpdateProfileOutputData(String username, String message){
        this.username = username;
        this.message = message;
    }
    public String getUsername(){return username; }
    public String getMessage(){return message; }
}
