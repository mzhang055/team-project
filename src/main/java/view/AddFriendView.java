package view;

import interface_adapter.add_friend.AddFriendController;
import interface_adapter.add_friend.AddFriendViewModel;
import javax.swing.*;
import java.awt.*;

public class AddFriendView extends JPanel {
    private final AddFriendController controller;
    private final AddFriendViewModel viewModel;
    private final ViewManager viewManager;

    private final JTextField friendUsernameField = new JTextField(15);
    private final JLabel messageLabel = new JLabel("");

    public AddFriendView(AddFriendController controller, AddFriendViewModel viewModel, ViewManager viewManager){
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
        add(new JLabel("Friend's Username:"), c);
        c.gridx = 1;
        add(friendUsernameField, c);
        row++;

        JButton sendButton = new JButton("Send Friend Request");
        JButton backButton = new JButton("Back to Main");

        c.gridx = 0;
        c.gridy = row;
        add(sendButton, c);
        c.gridx = 1;
        add(backButton, c);
        row++;

        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        messageLabel.setForeground(Color.RED);
        add(messageLabel, c);

        sendButton.addActionListener(e -> onSend());
        backButton.addActionListener(e -> viewManager.showMainBoardView());
    }

    private void onSend(){
        String targetUsername = friendUsernameField.getText().trim();
        String requester = viewManager.getCurrentUsername();

        if (requester == null || requester.isEmpty()){
            messageLabel.setText("You are not logged in");
            return;
        }
        if (targetUsername.isEmpty()){
            messageLabel.setText("Please enter a username");
            return;
        }
        controller.addFriend(requester, targetUsername);
        messageLabel.setText(viewModel.getMessage());
    }
}
