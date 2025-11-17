package view;

import entities.Recipe;
import interface_adapter.recipe.RecipeSavedController;
import interface_adapter.recipe.RecipeSavedViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RecipeSavedView extends JFrame {

    private final RecipeSavedController controller;
    private final RecipeSavedViewModel viewModel;

    private DefaultListModel<String> listModel;
    private JList<String> recipeList;
    private JTextArea detailArea;

    public RecipeSavedView(RecipeSavedController controller,
                           RecipeSavedViewModel viewModel) {
        super("Recipe Saved");
        this.controller = controller;
        this.viewModel = viewModel;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        initUI();
        refreshFromUseCase();
    }

    private void initUI() {
        listModel = new DefaultListModel<>();
        recipeList = new JList<>(listModel);
        recipeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        detailArea = new JTextArea();
        detailArea.setEditable(false);
        detailArea.setLineWrap(true);
        detailArea.setWrapStyleWord(true);

        JScrollPane listScroll = new JScrollPane(recipeList);
        JScrollPane detailScroll = new JScrollPane(detailArea);

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, listScroll, detailScroll);
        splitPane.setDividerLocation(200);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshFromUseCase());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(refreshButton);

        Container content = getContentPane();
        content.setLayout(new BorderLayout(10, 10));
        content.add(splitPane, BorderLayout.CENTER);
        content.add(bottomPanel, BorderLayout.SOUTH);
        ((JComponent) content).setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        recipeList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                onSelected(recipeList.getSelectedIndex());
            }
        });
    }

    private void refreshFromUseCase() {
        controller.refresh();

        listModel.clear();
        List<Recipe> recipes = viewModel.getRecipes();
        String error = viewModel.getErrorMessage();

        if (!error.isEmpty()) {
            detailArea.setText(error);
            return;
        }

        if (recipes.isEmpty()) {
            detailArea.setText("No recipes saved yet.");
            return;
        }

        for (Recipe r : recipes) {
            String summary = nullSafe(r.getName())
                    + " (" + nullSafe(r.getCategory())
                    + ", " + nullSafe(r.getArea()) + ")";
            listModel.addElement(summary);
        }

        if (!recipes.isEmpty()) {
            recipeList.setSelectedIndex(0);
        }
    }

    private void onSelected(int index) {
        List<Recipe> recipes = viewModel.getRecipes();
        if (index < 0 || index >= recipes.size()) {
            detailArea.setText("");
            return;
        }
        Recipe r = recipes.get(index);
        detailArea.setText(formatRecipe(r));
    }

    private String formatRecipe(Recipe r) {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(nullSafe(r.getName())).append("\n");
        sb.append("Category: ").append(nullSafe(r.getCategory())).append("\n");
        sb.append("Area: ").append(nullSafe(r.getArea())).append("\n");
        sb.append("Image: ").append(nullSafe(r.getImageUrl())).append("\n\n");

        sb.append("Ingredients:\n");
        for (var ing : r.getIngredients()) {
            sb.append(" - ").append(ing).append("\n");
        }

        sb.append("\nInstructions:\n");
        sb.append(nullSafe(r.getInstructions())).append("\n");
        return sb.toString();
    }

    private String nullSafe(String s) {
        return s == null ? "" : s;
    }
}
