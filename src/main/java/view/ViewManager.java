package view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ViewManager {
    private final JFrame mainFrame;
    private final JPanel contentPanel;
    private final Map<String, JPanel> views = new HashMap<>();

    private String currentUsername;

    public ViewManager(JFrame mainFrame, JPanel contentPanel){
        this.mainFrame = mainFrame;
        this.contentPanel = contentPanel;
    }

    public void addView(String name, JPanel view){
        views.put(name, view);
    }

    public void showView(String name){
        JPanel view = views.get(name);
        if (view == null){
            System.out.println("View not found:" + name);
            return;
        }

        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(view, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void showLoginView(){
        showView("login");
    }

    public void showCreateAccountView(){
        showView("create_account");
    }

    public void showMainBoardView(){
        showView("main_board");
    }

    public void showAddFriendView(){
        showView("add_friend");
    }

    public void setCurrentUsername(String username){
        this.currentUsername = username;
    }

    public String getCurrentUsername(){
        return currentUsername;
    }
}
