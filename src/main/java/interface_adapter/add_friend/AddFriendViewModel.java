package interface_adapter.add_friend;

import interface_adapter.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddFriendViewModel extends ViewModel {
    private String message;
    private boolean success;
    private List<String> incomingRequests = new ArrayList<>();

    public String getMessage(){return message; }
    public boolean isSuccess(){return success; }
    public AddFriendViewModel() {
        super("Add Friend");
    }
    public List<String> getIncomingRequests () {return incomingRequests; }

    public void setMessage(String message){this.message = message; }
    public void setSuccess(boolean success){this.success = success; }
    public void setIncomingRequests(List<String> incoming) {
        this.incomingRequests = new ArrayList<>(incoming);
        firePropertyChange();
    }
}
