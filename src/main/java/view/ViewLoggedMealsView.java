package view;

import entities.Meal;
import entities.MealType;
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
    private java.util.List<Meal> mealsCache;

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

        // Store actual meal objects for editing
        mealsCache = new java.util.ArrayList<>();

        mealsTable = new JTable(tableModel);
        mealsTable.setFont(new Font("Monospaced", Font.PLAIN, 12));
        mealsTable.setRowHeight(25);
        mealsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(mealsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton editButton = new JButton("Edit Selected");
        editButton.addActionListener(e -> editSelectedMeal());
        bottomPanel.add(editButton);

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
        mealsCache.clear();

        // Get all meals for the user
        List<Meal> meals = mealDataAccess.getMealsByUserId(userId);

        if (meals.isEmpty()) {
            // Show empty message
            Object[] emptyRow = {"No meals logged yet", "", "", "", "", "", "", "", ""};
            tableModel.addRow(emptyRow);
        } else {
            // Add each meal to the table
            for (Meal meal : meals) {
                mealsCache.add(meal);
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

    private void editSelectedMeal() {
        int selectedRow = mealsTable.getSelectedRow();
        if (selectedRow < 0 || mealsCache.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a meal to edit", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Meal selectedMeal = mealsCache.get(selectedRow);
        showEditDialog(selectedMeal);
    }

    private void showEditDialog(Meal meal) {
        JDialog editDialog = new JDialog(this, "Edit Meal", true);
        editDialog.setLayout(new BorderLayout(10, 10));
        editDialog.setSize(500, 400);

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Food Name
        inputPanel.add(new JLabel("Food Name:"));
        JTextField foodNameField = new JTextField(meal.getFoodName());
        inputPanel.add(foodNameField);

        // Meal Type
        inputPanel.add(new JLabel("Meal Type:"));
        JComboBox<MealType> mealTypeCombo = new JComboBox<>(MealType.values());
        mealTypeCombo.setSelectedItem(meal.getMealType());
        inputPanel.add(mealTypeCombo);

        // Nutritional fields
        entities.NutritionalInfo nutInfo = meal.getNutritionalInfo();

        inputPanel.add(new JLabel("Calories:"));
        JTextField caloriesField = new JTextField(nutInfo != null ? String.valueOf(nutInfo.getCalories()) : "0");
        inputPanel.add(caloriesField);

        inputPanel.add(new JLabel("Protein (g):"));
        JTextField proteinField = new JTextField(nutInfo != null ? String.valueOf(nutInfo.getProtein()) : "0");
        inputPanel.add(proteinField);

        inputPanel.add(new JLabel("Carbs (g):"));
        JTextField carbsField = new JTextField(nutInfo != null ? String.valueOf(nutInfo.getCarbohydrates()) : "0");
        inputPanel.add(carbsField);

        inputPanel.add(new JLabel("Fat (g):"));
        JTextField fatField = new JTextField(nutInfo != null ? String.valueOf(nutInfo.getFat()) : "0");
        inputPanel.add(fatField);

        inputPanel.add(new JLabel("Fiber (g):"));
        JTextField fiberField = new JTextField(nutInfo != null ? String.valueOf(nutInfo.getFiber()) : "0");
        inputPanel.add(fiberField);

        editDialog.add(inputPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            try {
                // Parse input values
                String foodName = foodNameField.getText().trim();
                MealType mealType = (MealType) mealTypeCombo.getSelectedItem();
                double calories = Double.parseDouble(caloriesField.getText().trim());
                double protein = Double.parseDouble(proteinField.getText().trim());
                double carbs = Double.parseDouble(carbsField.getText().trim());
                double fat = Double.parseDouble(fatField.getText().trim());
                double fiber = Double.parseDouble(fiberField.getText().trim());

                if (foodName.isEmpty()) {
                    JOptionPane.showMessageDialog(editDialog, "Food name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create updated nutritional info
                entities.NutritionalInfo updatedNutInfo = entities.NutritionalInfo.builder()
                        .calories(calories)
                        .protein(protein)
                        .carbohydrates(carbs)
                        .fat(fat)
                        .fiber(fiber)
                        .sugar(nutInfo != null ? nutInfo.getSugar() : 0)
                        .build();

                // Create updated meal with same ID
                Meal updatedMeal = Meal.builder(foodName)
                        .id(meal.getId())
                        .mealType(mealType)
                        .nutritionalInfo(updatedNutInfo)
                        .loggedAt(meal.getLoggedAt())
                        .userId(meal.getUserId())
                        .build();

                // Save to data access
                if (mealDataAccess.update(updatedMeal)) {
                    JOptionPane.showMessageDialog(editDialog, "Meal updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    editDialog.dispose();
                    loadMeals(); // Refresh the table
                } else {
                    JOptionPane.showMessageDialog(editDialog, "Failed to update meal", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(editDialog, "Please enter valid numbers for nutritional values", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(editDialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> editDialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        editDialog.add(buttonPanel, BorderLayout.SOUTH);

        editDialog.setLocationRelativeTo(this);
        editDialog.setVisible(true);
    }
}
