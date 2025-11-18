package app;

import data_access.FileRecipeLogDataAccessObject;
import data_access.TheMealDBRecipeDataAccessObject;
import interface_adapter.recipe.*;
import use_case.recipe_log.*;
import use_case.recipe_search.SearchRecipeInteractor;
import use_case.recipe_search.RecipeSearchDataAccessInterface;
import use_case.recipe_search.SearchRecipeInputBoundary;
import use_case.recipe_search.SearchRecipeOutputBoundary;
import view.RecipeMenuView;

import javax.swing.*;

public class RecipeMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                RecipeSearchDataAccessInterface searchDAO =
                        new TheMealDBRecipeDataAccessObject();
                RecipeLogDataAccessInterface logDAO =
                        new FileRecipeLogDataAccessObject("recipes.ser");

                RecipeSearchViewModel searchVM = new RecipeSearchViewModel();
                RecipeSavedViewModel savedVM = new RecipeSavedViewModel();

                SearchRecipeOutputBoundary searchPresenter =
                        new RecipeSearchPresenter(searchVM);
                SaveRecipePresenter savePresenter =
                        new SaveRecipePresenter(searchVM);
                RecipeSavedPresenter savedPresenter =
                        new RecipeSavedPresenter(savedVM);

                SearchRecipeInputBoundary searchInteractor =
                        new SearchRecipeInteractor(searchDAO, searchPresenter);
                SaveRecipeInputBoundary saveInteractor =
                        new SaveRecipeInteractor(logDAO, savePresenter);
                GetSavedRecipesInputBoundary getSavedInteractor =
                        new GetSavedRecipesInteractor(logDAO, savedPresenter);

                RecipeSearchController searchController =
                        new RecipeSearchController(searchInteractor);
                SaveRecipeController saveController =
                        new SaveRecipeController(saveInteractor);
                RecipeSavedController savedController =
                        new RecipeSavedController(getSavedInteractor);

                RecipeMenuView menuView = new RecipeMenuView(
                        searchController, saveController, searchVM,
                        savedController, savedVM);
                menuView.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Failed to start Recipe module: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
