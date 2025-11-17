package view;

import entities.MealType;
import interface_adapter.LogMeals.LogMealsController;
import interface_adapter.LogMeals.LogMealsViewModel;
import use_case.LogMeals.MealDataAccessInterface;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * View for logging meals manually.
 */
public class LogMealsView extends JFrame implements PropertyChangeListener {

    private final LogMealsViewModel viewModel;
    private final LogMealsController controller;
    private final MealDataAccessInterface mealDataAccess;
    private final String userId;

    private JTextArea resultArea;
    private JTextField foodNameField;
    private JComboBox<MealType> mealTypeComboBox;
    private JButton saveButton;
    private JPanel buttonPanel;

    public LogMealsView(LogMealsViewModel viewModel, LogMealsController controller,
                        MealDataAccessInterface mealDataAccess, String userId) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.mealDataAccess = mealDataAccess;
        this.userId = userId;

        viewModel.addPropertyChangeListener(this);

        initializeUI();
    }

    private void initializeUI() {
        setTitle("Log Meals - Manual Entry");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Top panel with input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Meal Type:"));
        mealTypeComboBox = new JComboBox<>(MealType.values());
        inputPanel.add(mealTypeComboBox);

        inputPanel.add(new JLabel("Food Name:"));
        foodNameField = new JTextField();
        inputPanel.add(foodNameField);

        JButton fetchButton = new JButton("Fetch Nutrition");
        fetchButton.addActionListener(e -> fetchNutrition());
        inputPanel.add(new JLabel()); // Empty cell
        inputPanel.add(fetchButton);

        add(inputPanel, BorderLayout.NORTH);

        // Button panel at bottom
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        saveButton = new JButton("Save Meal");
        saveButton.setEnabled(false); // Disabled initially
        saveButton.addActionListener(e -> saveMeal());
        buttonPanel.add(saveButton);

        JButton viewMealsButton = new JButton("View All Logged Meals");
        viewMealsButton.addActionListener(e -> openViewLoggedMealsWindow());
        buttonPanel.add(viewMealsButton);

        // Center panel with results
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setText("Enter a food name and click 'Fetch Nutrition' to get nutrition info from CalorieNinjas API\n\n");

        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Allow Enter key to submit
        foodNameField.addActionListener(e -> fetchNutrition());
    }

    private void fetchNutrition() {
        String foodName = foodNameField.getText().trim();
        MealType mealType = (MealType) mealTypeComboBox.getSelectedItem();

        if (foodName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a food name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        resultArea.append("Fetching nutrition data for: " + foodName + "...\n");
        controller.logMeal(foodName, mealType, userId);
    }

    private void saveMeal() {
        // Meal is already saved during fetch, this just confirms to user
        JOptionPane.showMessageDialog(this, "Meal has been saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        saveButton.setEnabled(false); // Disable after saving
    }

    private void openViewLoggedMealsWindow() {
        ViewLoggedMealsView viewLoggedMealsView = new ViewLoggedMealsView(mealDataAccess, userId);
        viewLoggedMealsView.setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (viewModel.getSuccessMessage() != null) {
            resultArea.append("\n" + viewModel.getSuccessMessage() + "\n");
            resultArea.append("=" + "=".repeat(60) + "\n\n");
            foodNameField.setText("");
            // Enable save button after successful fetch
            saveButton.setEnabled(true);
        } else if (viewModel.getErrorMessage() != null) {
            resultArea.append("ERROR: " + viewModel.getErrorMessage() + "\n\n");
            JOptionPane.showMessageDialog(this, viewModel.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            // Disable save button on error
            saveButton.setEnabled(false);
        }
    }
}
