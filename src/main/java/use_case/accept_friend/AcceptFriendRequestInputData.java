package use_case.accept_friend;

public class AcceptFriendRequestInputData {
    private final String receiverUsername;
    private final String requesterUsername;

    public AcceptFriendRequestInputData(String receiverUsername, String requesterUsername){
        this.receiverUsername = receiverUsername;
        this.requesterUsername = requesterUsername;
    }

    public String getReceiverUsername(){return receiverUsername; }

    public String getRequesterUsername(){return requesterUsername; }

}
