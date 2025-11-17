package view;

import javax.swing.*;
import java.awt.*;

/**
 * The View for when the user is logged into the program.
 */
public class DashboardView extends JPanel {
    private final String viewName = "DashboardView";
    private JButton logMealButton;
    private JButton saveRecipeButton;
    private JButton setTargetButton;
    private JButton profileButton;
    private DefaultListModel<String> recipeModel;
    private JLabel calories;
    private JLabel remaining;
    private JLabel protein;
    private JLabel carbs;
    private JLabel fats;

    public DashboardView() {
        setLayout(new BorderLayout());

        buildRecipePanel();
        buildNutritionPanel();
        buildNavBar();
    }

    private void buildRecipePanel() {
        recipeModel = new DefaultListModel<>();
        JList<String> recipeList = new JList<>(recipeModel);
        add(new JScrollPane(recipeList), BorderLayout.WEST);
    }

    private void buildNutritionPanel() {
        calories = new JLabel("Calories: 0");
        remaining = new JLabel("Remaining: 0");
        protein = new JLabel("Protein: 0");
        carbs = new JLabel("Carbs: 0");
        fats = new JLabel("Fats: 0");

        JPanel nutrition = new JPanel();
        nutrition.setLayout(new BoxLayout(nutrition, BoxLayout.Y_AXIS));
        nutrition.add(calories);
        nutrition.add(remaining);
        nutrition.add(protein);
        nutrition.add(carbs);
        nutrition.add(fats);

        add(nutrition, BorderLayout.CENTER);
    }

    private void buildNavBar() {
        JPanel nav = new JPanel();
        nav.add(setTargetButton);
        nav.add(logMealButton);
        nav.add(logMealButton);
        nav.add(profileButton);

        add(nav, BorderLayout.SOUTH);
    }

    public String getViewName() {
        return viewName;
    }
}
