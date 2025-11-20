package app;

import data_access.InMemoryUserDataAccessObject;
import data_access.TheMealDBRecipeDataAccessObject;
import entities.User;
import interface_adapter.recipe.*;
import use_case.recipe_log.*;
import use_case.recipe_search.RecipeSearchDataAccessInterface;
import use_case.recipe_search.SearchRecipeInputBoundary;
import use_case.recipe_search.SearchRecipeInteractor;
import use_case.recipe_search.SearchRecipeOutputBoundary;
import use_case.shared.UserDataAccessInterface;
import view.RecipeMenuView;

import javax.swing.*;

public class RecipeMain {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                String username = "test_user";

                UserDataAccessInterface userDAO = new InMemoryUserDataAccessObject();
                User testUser = new User(username, "password123");
                userDAO.save(testUser);

                RecipeSearchDataAccessInterface searchDAO =
                        new TheMealDBRecipeDataAccessObject();

                RecipeSearchViewModel searchVM = new RecipeSearchViewModel();
                RecipeSavedViewModel savedVM = new RecipeSavedViewModel();

                SearchRecipeOutputBoundary searchPresenter =
                        new RecipeSearchPresenter(searchVM);
                SaveRecipeOutputBoundary savePresenter =
                        new SaveRecipePresenter(searchVM);
                RecipeSavedPresenter savedPresenter =
                        new RecipeSavedPresenter(savedVM);

                SearchRecipeInputBoundary searchInteractor =
                        new SearchRecipeInteractor(searchDAO, searchPresenter);

                SaveRecipeInputBoundary saveInteractor =
                        new SaveRecipeInteractor(userDAO, savePresenter);

                GetSavedRecipesInputBoundary getSavedInteractor =
                        new GetSavedRecipesInteractor(userDAO, searchDAO, savedPresenter);

                DeleteSavedRecipeInputBoundary deleteSavedInteractor =
                        new DeleteSavedRecipeInteractor(userDAO, savedPresenter);

                RecipeSearchController searchController =
                        new RecipeSearchController(searchInteractor);

                SaveRecipeController saveController =
                        new SaveRecipeController(saveInteractor);

                RecipeSavedController savedController =
                        new RecipeSavedController(getSavedInteractor, deleteSavedInteractor);

                RecipeMenuView menuView = new RecipeMenuView(
                        searchController,
                        saveController,
                        searchVM,
                        savedController,
                        savedVM,
                        username
                );
                menuView.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                        null,
                        "Failed to start Recipe module: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
