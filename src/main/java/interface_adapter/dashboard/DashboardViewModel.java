package interface_adapter.dashboard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DashboardViewModel {
    private final PropertyChangeSupport support;

    public DashboardViewModel(PropertyChangeSupport support) {
        this.support = support;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
