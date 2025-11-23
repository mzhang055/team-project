package use_case.remove_friend;

public class RemoveFriendInputData {
    final private String currentUsername;
    final private String friendUsername;

    public RemoveFriendInputData(String currentUsername, String friendUsername) {
        this.currentUsername = currentUsername;
        this.friendUsername = friendUsername;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public String getFriendUsername() {
        return friendUsername;
    }
}
