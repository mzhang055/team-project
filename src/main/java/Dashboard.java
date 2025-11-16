import javax.swing.*;
import java.awt.*;

public class Dashboard extends JPanel {
    private JLabel calories;
    private JLabel remainingCalories;
    private JLabel protein;
    private JLabel carbs;
    private JLabel fats;

    Dashboard(){
        //Todo: Replace w/ User obj
        JPanel recipesPanel = new JPanel(new BorderLayout());
        DefaultListModel<String> recipeModel = new DefaultListModel<>();
        //Todo: Fill RecipeList
        JList<String> recipeList = new JList<>(recipeModel);
        recipesPanel.add(new JScrollPane(recipeList), BorderLayout.CENTER);

        JPanel nutritionPanel = new JPanel();
        nutritionPanel.setLayout(new BoxLayout(nutritionPanel, BoxLayout.Y_AXIS));

        //TODO: Make Cleaner
        nutritionPanel.add(calories);
        nutritionPanel.add(remainingCalories);
        nutritionPanel.add(protein);
        nutritionPanel.add(carbs);
        nutritionPanel.add(fats);

        // View History
        JButton viewHistoryButton = new JButton("View History");
        nutritionPanel.add(viewHistoryButton);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, recipesPanel, nutritionPanel);
        add(splitPane, BorderLayout.CENTER);
    }
}
