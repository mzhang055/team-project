package use_case.reject_friend;

public class RejectFriendRequestOutputData {
    private final String receiverUsername;
    private final String requesterUsername;
    private final String message;

    public RejectFriendRequestOutputData(String receiverUsername,
                                         String requesterUsername, String message){
        this.receiverUsername = receiverUsername;
        this.requesterUsername = requesterUsername;
        this.message = message;
    }
    public String getReceiverUsername(){return receiverUsername; }
    public String getRequesterUsername(){return requesterUsername; }
    public String getMessage(){return message; }
}
