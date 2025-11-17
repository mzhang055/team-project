package view;

import entities.Meal;
import use_case.LogMeals.MealDataAccessInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * View for displaying all previously logged meals for a user.
 */
public class ViewLoggedMealsView extends JFrame {

    private final MealDataAccessInterface mealDataAccess;
    private final String userId;
    private JTable mealsTable;
    private DefaultTableModel tableModel;

    public ViewLoggedMealsView(MealDataAccessInterface mealDataAccess, String userId) {
        this.mealDataAccess = mealDataAccess;
        this.userId = userId;

        initializeUI();
        loadMeals();
    }

    private void initializeUI() {
        setTitle("My Logged Meals");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Header
        JLabel headerLabel = new JLabel("My Logged Meals", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(headerLabel, BorderLayout.NORTH);

        // Table for displaying meals
        String[] columnNames = {"Date", "Meal Type", "Food", "Calories", "Protein (g)", "Carbs (g)", "Fat (g)", "Fiber (g)", "Sugar (g)"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };

        mealsTable = new JTable(tableModel);
        mealsTable.setFont(new Font("Monospaced", Font.PLAIN, 12));
        mealsTable.setRowHeight(25);
        mealsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(mealsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with refresh button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadMeals());
        bottomPanel.add(refreshButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        bottomPanel.add(closeButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadMeals() {
        // Clear existing rows
        tableModel.setRowCount(0);

        // Get all meals for the user
        List<Meal> meals = mealDataAccess.getMealsByUserId(userId);

        if (meals.isEmpty()) {
            // Show empty message
            Object[] emptyRow = {"No meals logged yet", "", "", "", "", "", "", "", ""};
            tableModel.addRow(emptyRow);
        } else {
            // Add each meal to the table
            for (Meal meal : meals) {
                Object[] row = {
                    meal.getLoggedAt().toString().substring(0, 16), // Date and time
                    meal.getMealType().getDisplayName(),
                    meal.getFoodName(),
                    meal.getNutritionalInfo() != null ? String.format("%.1f", meal.getNutritionalInfo().getCalories()) : "N/A",
                    meal.getNutritionalInfo() != null ? String.format("%.1f", meal.getNutritionalInfo().getProtein()) : "N/A",
                    meal.getNutritionalInfo() != null ? String.format("%.1f", meal.getNutritionalInfo().getCarbohydrates()) : "N/A",
                    meal.getNutritionalInfo() != null ? String.format("%.1f", meal.getNutritionalInfo().getFat()) : "N/A",
                    meal.getNutritionalInfo() != null ? String.format("%.1f", meal.getNutritionalInfo().getFiber()) : "N/A",
                    meal.getNutritionalInfo() != null ? String.format("%.1f", meal.getNutritionalInfo().getSugar()) : "N/A"
                };
                tableModel.addRow(row);
            }
        }
    }
}
