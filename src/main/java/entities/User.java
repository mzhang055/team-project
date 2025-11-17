package entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private final String username;
    private String password;

    private double height;
    private double weight;
    private String allergies;
    private boolean vegan;

    private final List<String> friendsUsernames = new ArrayList<>();
    private final List<String> incomingFriendRequests = new ArrayList<>();
    private final List<String> outgoingFriendRequests = new ArrayList<>();
    private final List<String> mealIds = new ArrayList<>();

    public User(String username, String password) {
        this(username, password, 0.0, 0.0, "", false);
    }

    public User(String username, String password, double height, double weight,
                String allergies, boolean vegan){
        this.username = username;
        this.password = password;
        this.height = height;
        this.weight = weight;
        this.allergies = allergies;
        this.vegan = vegan;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public double getHeight(){return height; }
    public void setHeight(double height){this.height = height; }

    public double getWeight(){return weight; }
    public void setWeight(double weight){this.weight = weight; }

    public String getAllergies(){return allergies; }
    public void setAllergies(String allergies){this.allergies = allergies; }

    public boolean isVegan(){return vegan; }
    public void setVegan(boolean vegan){this.vegan = vegan; }

    public List<String> getFriendsUsernames() {
        return friendsUsernames;
    }
    public List<String> getIncomingFriendRequests(){return incomingFriendRequests; }
    public List<String> getOutgoingFriendRequests(){return outgoingFriendRequests; }

    public void addFriend(String friendUsername) {
        if (!friendsUsernames.contains(friendUsername)) {
            friendsUsernames.add(friendUsername);
        }
    }

    public void addIncomingRequest(String fromUsername){
        if (!incomingFriendRequests.contains(fromUsername)){
            incomingFriendRequests.add(fromUsername);
        }
    }

    public void removeIncomingRequest(String fromUsername){
        incomingFriendRequests.remove(fromUsername);
    }

    public void addOutgoingRequest(String toUsername){
        if (!outgoingFriendRequests.contains(toUsername)){
            outgoingFriendRequests.add(toUsername);
        }
    }

    public void removeOutgoingRequest(String toUsername){
        outgoingFriendRequests.remove(toUsername);
    }

    public List<String> getMealIds() {
        return mealIds;
    }

    public void addMeal(String mealId) {
        if (!mealIds.contains(mealId)) {
            mealIds.add(mealId);
        }
    }

    public void removeMeal(String mealId) {
        mealIds.remove(mealId);
    }
}
