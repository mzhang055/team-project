package view;

import entities.User;
import interface_adapter.Navigation;
import interface_adapter.add_friend.AddFriendController;
import interface_adapter.add_friend.AddFriendViewModel;
import interface_adapter.dashboard.DashboardController;
import interface_adapter.accept_friend.AcceptFriendRequestController;
import interface_adapter.reject_friend.RejectFriendRequestController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class AddFriendView extends JPanel implements PropertyChangeListener {
    public static final String viewName = "add friend";
    private AddFriendController addFriendController;
    private AcceptFriendRequestController acceptController;
    private RejectFriendRequestController rejectController;
    private final Navigation navigation;
    private final AddFriendViewModel viewModel;

    private JTextField friendUsernameField;
    private JButton sendRequestButton;
    private JLabel statusLabel;

    private DefaultListModel<String> requestListModel;
    private JList<String> requestList;
    private JButton accpetButton;
    private JButton rejectButton;
    private JButton backButton;

    public AddFriendView(AddFriendViewModel viewModel, Navigation navigation) {
        this.viewModel = viewModel;
        this.navigation = navigation;
        this.viewModel.addPropertyChangeListener(this);
        initUI();
        wireActions();
    }

    public void propertyChange(PropertyChangeEvent event) {
        java.util.List<String> incoming = viewModel.getIncomingRequests();
        requestList.setListData(incoming.toArray(new String[0]));
    }

    private void initUI(){
        setLayout(new BorderLayout());

        JPanel addFriendPanel = new JPanel();
        addFriendPanel.setLayout(new BoxLayout(addFriendPanel, BoxLayout.Y_AXIS));
        addFriendPanel.setBorder(BorderFactory.createTitledBorder("Add Friend"));

        friendUsernameField = new JTextField(15);
        sendRequestButton = new JButton("send request");
        statusLabel = new JLabel(" ");

        JPanel inputRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputRow.add(new JLabel("Friend username:"));
        inputRow.add(friendUsernameField);
        inputRow.add(sendRequestButton);

        addFriendPanel.add(inputRow);
        addFriendPanel.add(statusLabel);

        JPanel requestPanel = new JPanel(new BorderLayout());
        requestPanel.setBorder(BorderFactory.createTitledBorder("Incoming Requests"));

        requestListModel = new DefaultListModel<>();
        requestList = new JList<>(requestListModel);
        requestPanel.add(new JScrollPane(requestList), BorderLayout.CENTER);

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        accpetButton = new JButton("accept");
        rejectButton = new JButton("reject");
        buttonRow.add(accpetButton);
        buttonRow.add(rejectButton);
        requestPanel.add(buttonRow, BorderLayout.SOUTH);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        backButton = new JButton("back to Main");
        bottom.add(backButton);

        add(addFriendPanel, BorderLayout.WEST);
        add(requestPanel, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

    }

    private void wireActions(){
        sendRequestButton.addActionListener(e -> {
            String friendUsername = friendUsernameField.getText().trim();
            if (friendUsername.isEmpty()) {
                setStatus("Please enter a username");
                return;
            }
            String currentUser = navigation.getCurrentUsername();
            if (currentUser == null || currentUser.isEmpty()) {
                setStatus("No logged in user");
                return;
            }
            addFriendController.addFriend(currentUser, friendUsername);
        });

        accpetButton.addActionListener(e -> {
            if (acceptController == null) {
                return;
            }
            String selected = requestList.getSelectedValue();
            if (selected == null) {
                setStatus("Select a request to accept");
                return;
            }
            String currentUser = navigation.getCurrentUsername();
            acceptController.accept(currentUser, selected);
        });

        rejectButton.addActionListener(e -> {
            if (rejectController == null) {
                return;
            }
            String selected = requestList.getSelectedValue();
            if (selected == null) {
                setStatus("Select a request to reject");
                return;
            }
            String currentUser = navigation.getCurrentUsername();
            rejectController.reject(currentUser, selected);
        });
        backButton.addActionListener(e -> navigation.goTo("Dashboard"));
    }

    public void setStatus(String message) {
        statusLabel.setText(message);
    }

    public void updateIncomingRequest(List<String> requests) {
        requestListModel.clear();
        for (String r : requests) {
            requestListModel.addElement(r);
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setAddFriendController(AddFriendController controller) {
        this.addFriendController = controller;
    }

    public void setAcceptFriendController(AcceptFriendRequestController controller) {
        this.acceptController = controller;
    }

    public void setRejectFriendController(RejectFriendRequestController controller) {
        this.rejectController = controller;
    }

    public void refreshRequest(User user) {
        if (user == null) {
            requestList.setListData(new String[0]);
            return;
        }
        java.util.List<String> incoming = user.getIncomingFriendRequests();
        requestList.setListData(incoming.toArray(new String[0]));
    }
}
