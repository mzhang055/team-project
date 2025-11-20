package use_case.profile;

public class ProfileInputData {
    private final String userId;

    public  ProfileInputData(String userId) {
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }
}
