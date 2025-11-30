package view;

import interface_adapter.LegacyViewManagerModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View Manager for the program. It listens for property change events
 * in the ViewManagerModel and updates which View should be visible.
 */
public class ViewManager implements PropertyChangeListener {

    private final CardLayout cardLayout;
    private final JPanel views;
    private final ViewManagerModel viewManagerModel;
    private final LegacyViewManagerModel legacy;

    public ViewManager(JPanel views, CardLayout cardLayout, ViewManagerModel viewManagerModel, LegacyViewManagerModel legacy) {
        this.views = views;
        this.cardLayout = cardLayout;
        this.viewManagerModel = viewManagerModel;
        this.legacy = legacy;
        this.viewManagerModel.addPropertyChangeListener(this);
        this.legacy.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String prop = evt.getPropertyName();
        if ("state".equals(prop)) {
            cardLayout.show(views, (String) evt.getNewValue());
        } else if ("legacyView".equals(prop)) {
            cardLayout.show(views, (String) evt.getNewValue());
        }
    }
}
