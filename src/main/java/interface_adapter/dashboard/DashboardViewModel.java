package interface_adapter.dashboard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DashboardViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private DashboardState state = new DashboardState();

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public DashboardState getState() {
        return state;
    }

    public void setState(DashboardState newState) {
        DashboardState oldState = this.state;
        this.state = newState;
        support.firePropertyChange("state", oldState, newState);
    }
}
