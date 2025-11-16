package use_case.create_account;

public class CreateAccountOutputData {
    private final String username;
    private final String message;

    public CreateAccountOutputData(String username, String message){
        this.username = username;
        this.message = message;
    }
    public String getUsername(){return username; }
    public String getMessage(){return message; }
}
