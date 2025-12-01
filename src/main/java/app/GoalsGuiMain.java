package app;

import use_case.goals.GoalCompletionHistory;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class GoalsGuiMain {

    public interface GoalListener {
        void onGoalSet(double calories,
                       double proteinGrams,
                       double carbGrams,
                       double fatGrams);
    }

    private static GoalListener listener;

    public static void open(GoalListener goalListener) {
        listener = goalListener;
        SwingUtilities.invokeLater(GoalsGuiMain::createAndShow);
    }

    private static boolean activeGoalSet = false;
    private static boolean activeGoalDone = false;

    public static boolean isActiveGoalSet() {
        return activeGoalSet;
    }

    public static boolean isActiveGoalDone() {
        return activeGoalDone;
    }

    private enum ActivityLevel {
        SEDENTARY("Sedentary (little/no exercise)", 1.2),
        LIGHT("Lightly active (1–3 days/week)", 1.375),
        MODERATE("Moderately active (3–5 days/week)", 1.55),
        ACTIVE("Very active (6–7 days/week)", 1.725),
        VERY_ACTIVE("Extra active (hard exercise, physical job)", 1.9);

        private final String label;
        private final double multiplier;

        ActivityLevel(String label, double multiplier) {
            this.label = label;
            this.multiplier = multiplier;
        }

        @Override
        public String toString() {
            return label;
        }

        public double getMultiplier() {
            return multiplier;
        }
    }

    private enum GoalType {
        LOSE("Lose weight"),
        MAINTAIN("Maintain weight"),
        GAIN("Gain weight");

        private final String label;

        GoalType(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    private enum Sex {
        MALE("Male"),
        FEMALE("Female");

        private final String label;

        Sex(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    private static double activeTargetCalories;
    private static double activeProteinGrams;
    private static double activeCarbGrams;
    private static double activeFatGrams;
    private static GoalType activeGoalType;
    private static double activeWeightKg;
    private static double activeHeightCm;
    private static int activeAgeYears;
    private static Sex activeSex;
    private static ActivityLevel activeActivityLevel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GoalsGuiMain::createAndShow);
    }

    private static void createAndShow() {
        JFrame frame = new JFrame("Nutrition Goal Helper");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Calculate Your Calories & Macros");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        root.add(title, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        root.add(center, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(
                BorderFactory.createTitledBorder("Your Info"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        JTextField weightField = new JTextField(10);
        JTextField heightField = new JTextField(10);
        JTextField ageField = new JTextField(10);
        JComboBox<Sex> sexBox = new JComboBox<>(Sex.values());
        JComboBox<ActivityLevel> activityBox = new JComboBox<>(ActivityLevel.values());
        JComboBox<GoalType> goalBox = new JComboBox<>(GoalType.values());

        int row = 0;

        c.gridx = 0; c.gridy = row;
        inputPanel.add(new JLabel("Weight (kg):"), c);
        c.gridx = 1;
        inputPanel.add(weightField, c);
        row++;

        c.gridx = 0; c.gridy = row;
        inputPanel.add(new JLabel("Height (cm):"), c);
        c.gridx = 1;
        inputPanel.add(heightField, c);
        row++;

        c.gridx = 0; c.gridy = row;
        inputPanel.add(new JLabel("Age (years):"), c);
        c.gridx = 1;
        inputPanel.add(ageField, c);
        row++;

        c.gridx = 0; c.gridy = row;
        inputPanel.add(new JLabel("Sex:"), c);
        c.gridx = 1;
        inputPanel.add(sexBox, c);
        row++;

        c.gridx = 0; c.gridy = row;
        inputPanel.add(new JLabel("Daily activity:"), c);
        c.gridx = 1;
        inputPanel.add(activityBox, c);
        row++;

        c.gridx = 0; c.gridy = row;
        inputPanel.add(new JLabel("Goal:"), c);
        c.gridx = 1;
        inputPanel.add(goalBox, c);
        row++;

        center.add(inputPanel);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBorder(
                BorderFactory.createTitledBorder("Calculated Plan (Last)"));

        JLabel maintenanceLabel = new JLabel("Maintenance: -");
        JLabel targetCalLabel = new JLabel("Target calories: -");
        JLabel proteinLabel = new JLabel("Protein: -");
        JLabel carbsLabel = new JLabel("Carbs: -");
        JLabel fatsLabel = new JLabel("Fats: -");

        resultPanel.add(maintenanceLabel);
        resultPanel.add(Box.createVerticalStrut(4));
        resultPanel.add(targetCalLabel);
        resultPanel.add(Box.createVerticalStrut(4));
        resultPanel.add(proteinLabel);
        resultPanel.add(carbsLabel);
        resultPanel.add(fatsLabel);

        center.add(Box.createVerticalStrut(10));
        center.add(resultPanel);

        JPanel activePanel = new JPanel(new GridBagLayout());
        activePanel.setBorder(
                BorderFactory.createTitledBorder("Active Goal"));

        GridBagConstraints ac = new GridBagConstraints();
        ac.insets = new Insets(3, 3, 3, 3);
        ac.fill = GridBagConstraints.HORIZONTAL;

        JLabel activeStatusLabel = new JLabel("No active goal set.");

        ac.gridx = 0; ac.gridy = 0; ac.gridwidth = 2;
        activePanel.add(activeStatusLabel, ac);
        ac.gridwidth = 1;

        JLabel calLabel = new JLabel("Calories (kcal/day):");
        JLabel protLabel = new JLabel("Protein (g/day):");
        JLabel carbLabel = new JLabel("Carbs (g/day):");
        JLabel fatLabel = new JLabel("Fats (g/day):");

        JTextField activeCaloriesField = new JTextField(10);
        JTextField activeProteinField = new JTextField(10);
        JTextField activeCarbsField = new JTextField(10);
        JTextField activeFatsField = new JTextField(10);

        activeCaloriesField.setEditable(false);
        activeProteinField.setEditable(false);
        activeCarbsField.setEditable(false);
        activeFatsField.setEditable(false);

        ac.gridy = 1;
        ac.gridx = 0;
        activePanel.add(calLabel, ac);
        ac.gridx = 1;
        activePanel.add(activeCaloriesField, ac);

        ac.gridy = 2;
        ac.gridx = 0;
        activePanel.add(protLabel, ac);
        ac.gridx = 1;
        activePanel.add(activeProteinField, ac);

        ac.gridy = 3;
        ac.gridx = 0;
        activePanel.add(carbLabel, ac);
        ac.gridx = 1;
        activePanel.add(activeCarbsField, ac);

        ac.gridy = 4;
        ac.gridx = 0;
        activePanel.add(fatLabel, ac);
        ac.gridx = 1;
        activePanel.add(activeFatsField, ac);

        center.add(Box.createVerticalStrut(10));
        center.add(activePanel);

        JButton calculateBtn = new JButton("Calculate Plan");
        JButton setGoalBtn = new JButton("Set as Active Goal");
        JButton editGoalBtn = new JButton("Edit Active Goal");
        JButton toggleDoneBtn = new JButton("Mark Goal as Done");

        setGoalBtn.setEnabled(false);
        editGoalBtn.setEnabled(false);
        toggleDoneBtn.setEnabled(false);

        JLabel infoLabel = new JLabel("Enter your info and click Calculate.");
        infoLabel.setForeground(Color.DARK_GRAY);

        JPanel bottom = new JPanel(new BorderLayout());
        JPanel buttonBox = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonBox.add(calculateBtn);
        buttonBox.add(setGoalBtn);
        buttonBox.add(editGoalBtn);
        buttonBox.add(toggleDoneBtn);

        bottom.add(buttonBox, BorderLayout.EAST);
        bottom.add(infoLabel, BorderLayout.WEST);
        root.add(bottom, BorderLayout.SOUTH);

        final double[] lastMaintenance = new double[1];
        final double[] lastTargetCalories = new double[1];
        final GoalType[] lastGoal = new GoalType[1];
        final MacroPlan[] lastPlan = new MacroPlan[1];
        final double[] lastWeightKg = new double[1];
        final double[] lastHeightCm = new double[1];
        final int[] lastAgeYears = new int[1];
        final Sex[] lastSex = new Sex[1];
        final ActivityLevel[] lastActivity = new ActivityLevel[1];
        final boolean[] editMode = new boolean[1];

        calculateBtn.addActionListener(e -> {
            try {
                double weightKg = Double.parseDouble(weightField.getText().trim());
                double heightCm = Double.parseDouble(heightField.getText().trim());
                int ageYears = Integer.parseInt(ageField.getText().trim());

                if (weightKg <= 0 || heightCm <= 0 || ageYears <= 0) {
                    throw new NumberFormatException();
                }

                ActivityLevel activity = (ActivityLevel) activityBox.getSelectedItem();
                GoalType goal = (GoalType) goalBox.getSelectedItem();
                Sex sex = (Sex) sexBox.getSelectedItem();

                double maintenance = estimateMaintenanceCalories(weightKg, heightCm, ageYears, sex, activity);
                double targetCalories = adjustForGoal(maintenance, goal);
                MacroPlan plan = calculateMacros(weightKg, targetCalories, goal);

                lastMaintenance[0] = maintenance;
                lastTargetCalories[0] = targetCalories;
                lastGoal[0] = goal;
                lastPlan[0] = plan;
                lastWeightKg[0] = weightKg;
                lastHeightCm[0] = heightCm;
                lastAgeYears[0] = ageYears;
                lastSex[0] = sex;
                lastActivity[0] = activity;

                DecimalFormat calFmt = new DecimalFormat("#");
                DecimalFormat gFmt = new DecimalFormat("#");

                maintenanceLabel.setText("Maintenance: " +
                        calFmt.format(maintenance) + " kcal/day");
                targetCalLabel.setText("Target calories: " +
                        calFmt.format(targetCalories) + " kcal/day");

                proteinLabel.setText("Protein: " +
                        gFmt.format(plan.proteinGrams) + " g/day (~" +
                        calFmt.format(plan.proteinGrams * 4) + " kcal)");
                fatsLabel.setText("Fats: " +
                        gFmt.format(plan.fatGrams) + " g/day (~" +
                        calFmt.format(plan.fatGrams * 9) + " kcal)");
                carbsLabel.setText("Carbs: " +
                        gFmt.format(plan.carbGrams) + " g/day (~" +
                        calFmt.format(plan.carbGrams * 4) + " kcal)");

                infoLabel.setText("Plan calculated. You can now set it as your active goal.");
                setGoalBtn.setEnabled(true);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Please enter valid numeric values for weight, height, and age.",
                        "Invalid input",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        setGoalBtn.addActionListener(e -> {
            if (lastPlan[0] == null) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Please calculate a plan first.",
                        "No plan",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            DecimalFormat plainFmt = new DecimalFormat("#");

            activeStatusLabel.setText("Status: ACTIVE (" +
                    (lastGoal[0] != null ? lastGoal[0].toString() : "Unknown goal") + ")");
            activeCaloriesField.setText(plainFmt.format(lastTargetCalories[0]));
            activeProteinField.setText(plainFmt.format(lastPlan[0].proteinGrams));
            activeCarbsField.setText(plainFmt.format(lastPlan[0].carbGrams));
            activeFatsField.setText(plainFmt.format(lastPlan[0].fatGrams));

            activeGoalSet = true;
            activeGoalDone = false;
            activeTargetCalories = lastTargetCalories[0];
            activeProteinGrams = lastPlan[0].proteinGrams;
            activeCarbGrams = lastPlan[0].carbGrams;
            activeFatGrams = lastPlan[0].fatGrams;
            activeGoalType = lastGoal[0];
            activeWeightKg = lastWeightKg[0];
            activeHeightCm = lastHeightCm[0];
            activeAgeYears = lastAgeYears[0];
            activeSex = lastSex[0];
            activeActivityLevel = lastActivity[0];

            activeCaloriesField.setEditable(false);
            activeProteinField.setEditable(false);
            activeCarbsField.setEditable(false);
            activeFatsField.setEditable(false);
            editMode[0] = false;
            editGoalBtn.setText("Edit Active Goal");

            toggleDoneBtn.setEnabled(true);
            editGoalBtn.setEnabled(true);
            infoLabel.setText("Active goal set. You can edit or mark it as done.");
            if (listener != null) {
                listener.onGoalSet(
                        activeTargetCalories,
                        activeProteinGrams,
                        activeCarbGrams,
                        activeFatGrams
                );}
        });

        editGoalBtn.addActionListener(e -> {
            if (!activeGoalSet) {
                JOptionPane.showMessageDialog(
                        frame,
                        "No active goal to edit. Set a goal first.",
                        "No active goal",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            if (!editMode[0]) {
                activeCaloriesField.setEditable(true);
                activeProteinField.setEditable(true);
                activeCarbsField.setEditable(true);
                activeFatsField.setEditable(true);
                editGoalBtn.setText("Save Edits");
                infoLabel.setText("Edit calories and macros, then click Save Edits.");
                editMode[0] = true;
            } else {
                try {
                    double newCals = Double.parseDouble(activeCaloriesField.getText().trim());
                    double newProt = Double.parseDouble(activeProteinField.getText().trim());
                    double newCarb = Double.parseDouble(activeCarbsField.getText().trim());
                    double newFat = Double.parseDouble(activeFatsField.getText().trim());

                    if (newCals <= 0 || newProt < 0 || newCarb < 0 || newFat < 0) {
                        throw new NumberFormatException();
                    }

                    activeTargetCalories = newCals;
                    activeProteinGrams = newProt;
                    activeCarbGrams = newCarb;
                    activeFatGrams = newFat;

                    activeCaloriesField.setEditable(false);
                    activeProteinField.setEditable(false);
                    activeCarbsField.setEditable(false);
                    activeFatsField.setEditable(false);
                    editGoalBtn.setText("Edit Active Goal");
                    infoLabel.setText("Edits saved for active goal.");
                    editMode[0] = false;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Please enter valid numeric values for calories and macros.",
                            "Invalid input",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        toggleDoneBtn.addActionListener(e -> {
            if (!activeGoalSet) {
                JOptionPane.showMessageDialog(
                        frame,
                        "No active goal to monitor. Set a goal first.",
                        "No active goal",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            activeGoalDone = !activeGoalDone;

            String currentUserId = "current_user";
            GoalCompletionHistory.markToday(currentUserId, activeGoalDone);
            GoalCompletionHistory.markToday(activeGoalDone);

            if (activeGoalDone) {
                activeStatusLabel.setText("Status: DONE");
                toggleDoneBtn.setText("Mark Goal as Not Done");
                infoLabel.setText("You have marked the goal as done.");
            } else {
                activeStatusLabel.setText("Status: ACTIVE (" +
                        (activeGoalType != null ? activeGoalType.toString() : "Unknown goal") + ")");
                toggleDoneBtn.setText("Mark Goal as Done");
                infoLabel.setText("Goal set back to active.");
            }
        });

        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static double estimateMaintenanceCalories(double weightKg,
                                                      double heightCm,
                                                      int ageYears,
                                                      Sex sex,
                                                      ActivityLevel level) {
        double sexConst = (sex == Sex.MALE) ? 5 : -161;
        double bmr = 10 * weightKg + 6.25 * heightCm - 5 * ageYears + sexConst;
        return bmr * level.getMultiplier();
    }

    private static double adjustForGoal(double maintenance, GoalType goal) {
        double target = maintenance;
        switch (goal) {
            case LOSE:
                target = maintenance - 350;
            case MAINTAIN:
                target = maintenance;
            case GAIN:
                target = maintenance + 300;
        }

        if (target < 1200) target = 1200;
        return target;
    }

    private static class MacroPlan {
        final double proteinGrams;
        final double carbGrams;
        final double fatGrams;

        MacroPlan(double p, double c, double f) {
            this.proteinGrams = p;
            this.carbGrams = c;
            this.fatGrams = f;
        }
    }

    private static MacroPlan calculateMacros(double weightKg,
                                             double targetCalories,
                                             GoalType goal) {
        double proteinPerKg;
        switch (goal) {
            case LOSE:
                proteinPerKg = 2.0;
            case MAINTAIN:
                proteinPerKg = 1.8;
            case GAIN:
                proteinPerKg = 1.8;
            default:
                proteinPerKg = 1.8;
        }
        double proteinGrams = proteinPerKg * weightKg;

        double fatCalories = targetCalories * 0.25;
        double fatGrams = fatCalories / 9.0;

        double proteinCalories = proteinGrams * 4;
        double carbCalories = targetCalories - proteinCalories - fatCalories;
        if (carbCalories < 0) carbCalories = 0;
        double carbGrams = carbCalories / 4.0;

        return new MacroPlan(proteinGrams, carbGrams, fatGrams);
    }
}
