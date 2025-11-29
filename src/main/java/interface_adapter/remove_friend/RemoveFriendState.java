package interface_adapter.remove_friend;

public class RemoveFriendState {
    private String currentUsername = "";
    private String friendUsername = "";
    private String message = "";
    private boolean removeFriendSuccess = false;
    public RemoveFriendState() {}
    public RemoveFriendState(RemoveFriendState copy) {
        this.currentUsername = copy.currentUsername;
        this.friendUsername = copy.friendUsername;
        this.message = copy.message;
        this.removeFriendSuccess = copy.removeFriendSuccess;
    }
    public String getCurrentUsername() {
        return currentUsername;
    }
    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }
    public String getFriendUsername() {
        return friendUsername;
    }
    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isRemoveFriendSuccess() {
        return removeFriendSuccess;
    }
    public void setRemoveFriendSuccess(boolean removeFriendSuccess) {
        this.removeFriendSuccess = removeFriendSuccess;
    }
    @Override
    public String toString() {
        return "RemoveFriendState{" +
                "currentUsername='" + currentUsername + '\'' +
                ", friendUsername='" + friendUsername + '\'' +
                ", message='" + message + '\'' +
                ", removeFriendSuccess=" + removeFriendSuccess +
                '}';
    }
}
