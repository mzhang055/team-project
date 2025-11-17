package use_case.add_friend;

public class AddFriendOutputData {
    private final String requesterUsername;
    private final String targetUsername;
    private final String message;

    public AddFriendOutputData(String requesterUsername, String targetUsername, String message){
        this.requesterUsername = requesterUsername;
        this.targetUsername = targetUsername;
        this.message = message;
    }
    public String getRequesterUsername(){return requesterUsername; }
    public String getTargetUsername(){return targetUsername; }
    public String getMessage(){return message; }
}
