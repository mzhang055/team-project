package interface_adapter.remove_friend;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class RemoveFriendViewModel {
    public static final String TITLE_LABEL = "Remove Friend";
    public static final String FRIEND_USERNAME_LABEL = "Friend Username";
    public static final String REMOVE_BUTTON_LABEL = "Remove Friend";
    public static final String BACK_BUTTON_LABEL = "Back";
    public static final String REMOVE_FRIEND_PROPERTY = "removeFriendState";

    private RemoveFriendState state = new RemoveFriendState();

    public RemoveFriendViewModel() {}

    public void setState(RemoveFriendState state) {
        this.state = state;
    }
    public RemoveFriendState getState() {
        return state;
    }
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    public void firePropertyChange() {
        support.firePropertyChange(REMOVE_FRIEND_PROPERTY, null, this.state);
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
