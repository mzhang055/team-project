package interface_adapter.remove_friend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveFriendDialog {
    private JDialog dialog;
    private JLabel messageLabel;
    private boolean userConfirmed = false;

    public boolean showDialog(String friendUsername) {
        dialog = new JDialog();
        dialog.setTitle("remove friend");
        dialog.setModal(true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        messageLabel = new JLabel("Remove " + friendUsername + " from your friend?");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(messageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");

        yesButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                userConfirmed = true;
                dialog.dispose();
            }
        });
        noButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                userConfirmed = false;
                dialog.dispose();
            }
        });
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(mainPanel);
        dialog.setVisible(true);
        return userConfirmed;

    }
}
