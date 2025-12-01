package use_case.reject_friend;

public class RejectFriendRequestInputData {
    private final String receiverUsername;
    private final String requesterUsername;

    public RejectFriendRequestInputData(String receiverUsername, String requesterUsername){
        this.receiverUsername = receiverUsername;
        this.requesterUsername = requesterUsername;
    }
    public String getReceiverUsername(){return receiverUsername; }
    public String getRequesterUsername(){return requesterUsername; }
}
