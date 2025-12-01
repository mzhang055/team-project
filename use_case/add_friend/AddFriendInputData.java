package use_case.add_friend;

public class AddFriendInputData {
    private final String requesterUsername;
    private final String targetUsername;

    public AddFriendInputData(String requesterUsername, String targetUsername){
        this.requesterUsername = requesterUsername;
        this.targetUsername = targetUsername;
    }

    public String getRequesterUsername(){return requesterUsername; }
    public String getTargetUsername(){return targetUsername; }
}
