package interface_adapter.logout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;

public class LogoutUI extends JPanel {
    private LogoutController controller;
    private JButton logoutButton;

    public LogoutUI(LogoutController controller) {

        this.controller = controller;
        initializePanel();
    }

    private void initializePanel() {
    setLayout(new FlowLayout(FlowLayout.RIGHT));
    logoutButton = new JButton("Logout");
    logoutButton.addActionListener(e -> performLogout());
    add(logoutButton);
    }

    private boolean performLogout() {
        return controller.logoutCurrentUser();
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public void addLogoutListener(ActionListener actionListener) {
        logoutButton.addActionListener(actionListener);
    }
}

