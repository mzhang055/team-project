package view;

import interface_adapter.Navigation;
import interface_adapter.dashboard.DashboardController;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginViewModel;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {
    private final String viewName = "Login";
    private final LoginViewModel viewModel;
    private final Navigation navigation;
    private LoginController controller = null;

    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JLabel messageLabel = new JLabel(" ");

    public LoginView(LoginViewModel viewModel, Navigation navigation) {
        this.viewModel = viewModel;
        this.navigation = navigation;

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
        createAccountButton.addActionListener(e -> navigation.goTo("Create Account"));
    }

    private void onLogin(){
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        controller.login(username, password);
        messageLabel.setText(viewModel.getMessage());

        if (viewModel.isSuccess()){
            navigation.setCurrentUsername(username);
            navigation.goTo("Dashboard");
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController loginController) {
        this.controller = loginController;
    }
}
