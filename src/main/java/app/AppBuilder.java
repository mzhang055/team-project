package app;

import entity.UserFactory;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {

    private JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final UserFactory userFactory = new UserFactory();

    public AppBuilder() {cardPanel.setLayout(cardLayout);}
}
