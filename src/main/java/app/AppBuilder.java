package app;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {

    private JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    public AppBuilder() {cardPanel.setLayout(cardLayout);}
}
