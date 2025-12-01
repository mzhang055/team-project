package use_case.remove_friend;

public class RemoveFriendOutputData {
    final private String removedFriendUsername;
    final private boolean success;
    final private String message;
    public RemoveFriendOutputData(String removedFriendUsername, boolean success) {
        this.removedFriendUsername = removedFriendUsername;
        this.success = success;
        if (success){
            this.message = "Successfully removed the following friend username from the database.";
        }else{
            this.message = "Failed to remove friend.";
        }
    }
    public RemoveFriendOutputData(String removedFriendUsername, boolean success, String message) {
        this.removedFriendUsername = removedFriendUsername;
        this.success = success;
        this.message = message;
    }
    public String getRemovedFriendUsername() {
        return removedFriendUsername;
    }
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
