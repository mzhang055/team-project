package interface_adapter;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

public class LegacyViewManagerModel {
    private final JPanel contentPanel;
    private final Map<String, JPanel> views = new HashMap<>();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public LegacyViewManagerModel(JPanel contentPanel){
        this.contentPanel = contentPanel;
    }

    public void addView(String name, JPanel view){
        views.put(name, view);
    }

    public void showView(String name){
        JPanel view = views.get(name);

        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(view, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();

        support.firePropertyChange("legacyView", null, "Legacy");
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }
}
