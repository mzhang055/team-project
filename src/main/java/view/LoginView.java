package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginViewModel;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {
    private final LoginController controller;
    private final LoginViewModel viewModel;
    private final ViewManager viewManager;

    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JLabel messageLabel = new JLabel(" ");

    public LoginView(LoginController controller, LoginViewModel viewModel, ViewManager viewManager) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.viewManager = viewManager;

        initUI();
    }
    private void initUI(){
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        int row = 0;

        c.gridx = 0;
        c.gridy = row;
        add(new JLabel("Username:"), c);
        c.gridx = 1;
        add(usernameField, c);
        row++;

        c.gridx = 0;
        c.gridy = row;
        add(new JLabel("Password:"), c);
        c.gridx = 1;
        add(passwordField, c);
        row++;

        JButton loginButton = new JButton("Log in");
        JButton createAccountButton = new JButton("Create account");

        c.gridx = 0;
        c.gridy = row;
        add(loginButton, c);
        c.gridx = 1;
        add(createAccountButton, c);
        row++;

        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        messageLabel.setForeground(Color.RED);
        add(messageLabel, c);

        loginButton.addActionListener(e -> onLogin());
        createAccountButton.addActionListener(e -> viewManager.showCreateAccountView());
    }

    private void onLogin(){
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        controller.login(username, password);
        messageLabel.setText(viewModel.getMessage());

        if (viewModel.isSuccess()){
            viewManager.setCurrentUsername(username);
            viewManager.showMainBoardView();
        }
    }
}
