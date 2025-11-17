package use_case.accept_friend;

public class AcceptFriendRequestInputData {
    private final String receiverUsesrname;
    private final String requesterUsername;

    public AcceptFriendRequestInputData(String receiverUsesrname, String requesterUsername){
        this.receiverUsesrname = receiverUsesrname;
        this.requesterUsername = requesterUsername;
    }

    public String getReceiverUsesrname(){return receiverUsesrname; }
    public String getRequesterUsername(){return requesterUsername; }

}
