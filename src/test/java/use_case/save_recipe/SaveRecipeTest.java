package use_case.save_recipe;

import data_access.RecipeDataAccessInterface;
import data_access.UserDataAccessInterface;
import entities.Recipe;
import entities.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SaveRecipeInteractor.
 * This class is written without any mocking framework.
 * It aims to cover 100% of:
 * - SaveRecipeInteractor
 * - SaveRecipeInputData
 * - SaveRecipeOutputData
 */
public class SaveRecipeTest {

    private static class RecordingPresenter implements SaveRecipeOutputBoundary {
        SaveRecipeOutputData lastOutput;

        @Override
        public void present(SaveRecipeOutputData outputData) {
            this.lastOutput = outputData;
        }
    }

    private Recipe createRecipe(String id, String name) {
        String category = "category";
        String area = "area";
        String instructions = "instructions";
        String imageUrl = "imageUrl";
        return new Recipe(id, name, category, area, instructions, imageUrl, null);
    }

    private User createUser(String username) {
        return new User(username, "password");
    }


    @Test
    void testNullUsername() {
        UserDataAccessInterface userDAO = new UserDataAccessInterface() {
            @Override
            public boolean existsByUsername(String username) {
                fail("existsByUsername should not be called when username is null");
                return false;
            }

            @Override
            public User getUser(String username) {
                fail("getUser should not be called when username is null");
                return null;
            }

            @Override
            public void save(User user) {
                fail("save(User) should not be called when username is null");
            }

            @Override
            public List<User> getAllUsers() {
                return Collections.emptyList();
            }
        };

        RecipeDataAccessInterface recipeDAO = new RecipeDataAccessInterface() {
            @Override
            public boolean save(String username, Recipe recipe) {
                fail("recipe save should not be called when username is null");
                return false;
            }

            @Override
            public List<Recipe> getRecipesByUser(String username) {
                return Collections.emptyList();
            }

            @Override
            public Optional<Recipe> getRecipeById(String recipeId) {
                return Optional.empty();
            }

            @Override
            public boolean delete(String username, String recipeId) {
                return false;
            }

            @Override
            public boolean hasSavedRecipes(String username) {
                return false;
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        SaveRecipeInteractor interactor =
                new SaveRecipeInteractor(userDAO, recipeDAO, presenter);

        SaveRecipeInputData input = new SaveRecipeInputData(null, null);
        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("No username provided.", presenter.lastOutput.getMessage());
    }


    @Test
    void testEmptyUsername() {
        UserDataAccessInterface userDAO = new UserDataAccessInterface() {
            @Override
            public boolean existsByUsername(String username) {
                fail("existsByUsername should not be called when username is empty");
                return false;
            }

            @Override
            public User getUser(String username) {
                fail("getUser should not be called when username is empty");
                return null;
            }

            @Override
            public void save(User user) {
                fail("save(User) should not be called when username is empty");
            }

            @Override
            public List<User> getAllUsers() {
                return Collections.emptyList();
            }
        };

        RecipeDataAccessInterface recipeDAO = new RecipeDataAccessInterface() {
            @Override
            public boolean save(String username, Recipe recipe) {
                fail("recipe save should not be called when username is empty");
                return false;
            }

            @Override
            public List<Recipe> getRecipesByUser(String username) {
                return Collections.emptyList();
            }

            @Override
            public Optional<Recipe> getRecipeById(String recipeId) {
                return Optional.empty();
            }

            @Override
            public boolean delete(String username, String recipeId) {
                return false;
            }

            @Override
            public boolean hasSavedRecipes(String username) {
                return false;
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        SaveRecipeInteractor interactor =
                new SaveRecipeInteractor(userDAO, recipeDAO, presenter);

        SaveRecipeInputData input = new SaveRecipeInputData("", null);
        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("No username provided.", presenter.lastOutput.getMessage());
    }


    @Test
    void testNullRecipe() {
        UserDataAccessInterface userDAO = new UserDataAccessInterface() {
            @Override
            public boolean existsByUsername(String username) {
                fail("existsByUsername should not be called when recipe is null");
                return false;
            }

            @Override
            public User getUser(String username) {
                fail("getUser should not be called when recipe is null");
                return null;
            }

            @Override
            public void save(User user) {
                fail("save(User) should not be called when recipe is null");
            }

            @Override
            public List<User> getAllUsers() {
                return Collections.emptyList();
            }
        };

        RecipeDataAccessInterface recipeDAO = new RecipeDataAccessInterface() {
            @Override
            public boolean save(String username, Recipe recipe) {
                fail("recipe save should not be called when recipe is null");
                return false;
            }

            @Override
            public List<Recipe> getRecipesByUser(String username) {
                return Collections.emptyList();
            }

            @Override
            public Optional<Recipe> getRecipeById(String recipeId) {
                return Optional.empty();
            }

            @Override
            public boolean delete(String username, String recipeId) {
                return false;
            }

            @Override
            public boolean hasSavedRecipes(String username) {
                return false;
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        SaveRecipeInteractor interactor =
                new SaveRecipeInteractor(userDAO, recipeDAO, presenter);

        SaveRecipeInputData input = new SaveRecipeInputData("alice", null);
        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("No recipe selected.", presenter.lastOutput.getMessage());
    }


    @Test
    void testRecipeWithoutId() {
        UserDataAccessInterface userDAO = new UserDataAccessInterface() {
            @Override
            public boolean existsByUsername(String username) {
                fail("existsByUsername should not be called when recipe has no id");
                return false;
            }

            @Override
            public User getUser(String username) {
                fail("getUser should not be called when recipe has no id");
                return null;
            }

            @Override
            public void save(User user) {
                fail("save(User) should not be called when recipe has no id");
            }

            @Override
            public List<User> getAllUsers() {
                return Collections.emptyList();
            }
        };

        RecipeDataAccessInterface recipeDAO = new RecipeDataAccessInterface() {
            @Override
            public boolean save(String username, Recipe recipe) {
                fail("recipe save should not be called when recipe has no id");
                return false;
            }

            @Override
            public List<Recipe> getRecipesByUser(String username) {
                return Collections.emptyList();
            }

            @Override
            public Optional<Recipe> getRecipeById(String recipeId) {
                return Optional.empty();
            }

            @Override
            public boolean delete(String username, String recipeId) {
                return false;
            }

            @Override
            public boolean hasSavedRecipes(String username) {
                return false;
            }
        };

        Recipe recipe = createRecipe(null, "NoIdRecipe");

        RecordingPresenter presenter = new RecordingPresenter();
        SaveRecipeInteractor interactor =
                new SaveRecipeInteractor(userDAO, recipeDAO, presenter);

        SaveRecipeInputData input = new SaveRecipeInputData("alice", recipe);
        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("Recipe has no id and cannot be saved.", presenter.lastOutput.getMessage());
    }


    @Test
    void testUserNotFound() {
        UserDataAccessInterface userDAO = new UserDataAccessInterface() {
            @Override
            public boolean existsByUsername(String username) {
                return false;
            }

            @Override
            public User getUser(String username) {
                fail("getUser should not be called when user does not exist");
                return null;
            }

            @Override
            public void save(User user) {
                fail("save(User) should not be called when user does not exist");
            }

            @Override
            public List<User> getAllUsers() {
                return Collections.emptyList();
            }
        };

        RecipeDataAccessInterface recipeDAO = new RecipeDataAccessInterface() {
            @Override
            public boolean save(String username, Recipe recipe) {
                fail("recipe save should not be called when user does not exist");
                return false;
            }

            @Override
            public List<Recipe> getRecipesByUser(String username) {
                return Collections.emptyList();
            }

            @Override
            public Optional<Recipe> getRecipeById(String recipeId) {
                return Optional.empty();
            }

            @Override
            public boolean delete(String username, String recipeId) {
                return false;
            }

            @Override
            public boolean hasSavedRecipes(String username) {
                return false;
            }
        };

        Recipe recipe = createRecipe("id-user-not-found", "Dish");

        RecordingPresenter presenter = new RecordingPresenter();
        SaveRecipeInteractor interactor =
                new SaveRecipeInteractor(userDAO, recipeDAO, presenter);

        SaveRecipeInputData input = new SaveRecipeInputData("missingUser", recipe);
        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("User not found: missingUser", presenter.lastOutput.getMessage());
    }

    @Test
    void testLocalRecipeSaveFails() {
        class StubUserDAO implements UserDataAccessInterface {
            boolean existsCalled;
            boolean getUserCalled;
            boolean saveCalled;
            User storedUser;

            @Override
            public boolean existsByUsername(String username) {
                existsCalled = true;
                return true;
            }

            @Override
            public User getUser(String username) {
                getUserCalled = true;
                storedUser = createUser(username);
                return storedUser;
            }

            @Override
            public void save(User user) {
                saveCalled = true;
                storedUser = user;
            }

            @Override
            public List<User> getAllUsers() {
                return new ArrayList<>();
            }
        }

        class StubRecipeDAO implements RecipeDataAccessInterface {
            boolean saveCalled;

            @Override
            public boolean save(String username, Recipe recipe) {
                saveCalled = true;
                return false; // simulate failure
            }

            @Override
            public List<Recipe> getRecipesByUser(String username) {
                return new ArrayList<>();
            }

            @Override
            public Optional<Recipe> getRecipeById(String recipeId) {
                return Optional.empty();
            }

            @Override
            public boolean delete(String username, String recipeId) {
                return false;
            }

            @Override
            public boolean hasSavedRecipes(String username) {
                return false;
            }
        }

        StubUserDAO userDAO = new StubUserDAO();
        StubRecipeDAO recipeDAO = new StubRecipeDAO();
        RecordingPresenter presenter = new RecordingPresenter();
        SaveRecipeInteractor interactor =
                new SaveRecipeInteractor(userDAO, recipeDAO, presenter);

        Recipe recipe = createRecipe("r-fail", "Fail dish");
        SaveRecipeInputData input = new SaveRecipeInputData("bob", recipe);
        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals(
                "Recipe saved for user, but failed to save into local recipe DB.",
                presenter.lastOutput.getMessage()
        );

        assertTrue(userDAO.existsCalled);
        assertTrue(userDAO.getUserCalled);
        assertTrue(userDAO.saveCalled);
        assertTrue(recipeDAO.saveCalled);
    }

    @Test
    void testSuccessfulSave() {
        class StubUserDAO implements UserDataAccessInterface {
            boolean existsCalled;
            boolean getUserCalled;
            boolean saveCalled;

            @Override
            public boolean existsByUsername(String username) {
                existsCalled = true;
                return true;
            }

            @Override
            public User getUser(String username) {
                getUserCalled = true;
                return createUser(username);
            }

            @Override
            public void save(User user) {
                saveCalled = true;
            }

            @Override
            public List<User> getAllUsers() {
                return new ArrayList<>();
            }
        }

        class StubRecipeDAO implements RecipeDataAccessInterface {
            boolean saveCalled;

            @Override
            public boolean save(String username, Recipe recipe) {
                saveCalled = true;
                return true; // simulate success
            }

            @Override
            public List<Recipe> getRecipesByUser(String username) {
                return new ArrayList<>();
            }

            @Override
            public Optional<Recipe> getRecipeById(String recipeId) {
                return Optional.empty();
            }

            @Override
            public boolean delete(String username, String recipeId) {
                return false;
            }

            @Override
            public boolean hasSavedRecipes(String username) {
                return false;
            }
        }

        StubUserDAO userDAO = new StubUserDAO();
        StubRecipeDAO recipeDAO = new StubRecipeDAO();
        RecordingPresenter presenter = new RecordingPresenter();
        SaveRecipeInteractor interactor =
                new SaveRecipeInteractor(userDAO, recipeDAO, presenter);

        Recipe recipe = createRecipe("r-ok", "Nice dish");
        SaveRecipeInputData input = new SaveRecipeInputData("carol", recipe);
        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertTrue(presenter.lastOutput.isSuccess());
        assertEquals("Saved recipe: Nice dish", presenter.lastOutput.getMessage());

        assertTrue(userDAO.existsCalled);
        assertTrue(userDAO.getUserCalled);
        assertTrue(userDAO.saveCalled);
        assertTrue(recipeDAO.saveCalled);
    }

    @Test
    void testInputAndOutputData() {
        Recipe recipe = createRecipe("id-123", "Sample");
        SaveRecipeInputData input = new SaveRecipeInputData("tester", recipe);

        assertEquals("tester", input.getUsername());
        assertEquals(recipe, input.getRecipe());

        SaveRecipeOutputData output = new SaveRecipeOutputData(true, "OK");
        assertTrue(output.isSuccess());
        assertEquals("OK", output.getMessage());
    }

    @Test
    void testRecipeWithEmptyId() {
        UserDataAccessInterface userDAO = new UserDataAccessInterface() {
            @Override
            public boolean existsByUsername(String username) {
                fail("existsByUsername should not be called when recipe id is empty");
                return false;
            }

            @Override
            public User getUser(String username) {
                fail("getUser should not be called when recipe id is empty");
                return null;
            }

            @Override
            public void save(User user) {
                fail("save(User) should not be called when recipe id is empty");
            }

            @Override
            public java.util.List<User> getAllUsers() {
                return java.util.Collections.emptyList();
            }
        };

        RecipeDataAccessInterface recipeDAO = new RecipeDataAccessInterface() {
            @Override
            public boolean save(String username, Recipe recipe) {
                fail("save should not be called when recipe id is empty");
                return false;
            }

            @Override
            public java.util.List<Recipe> getRecipesByUser(String username) {
                return java.util.Collections.emptyList();
            }

            @Override
            public java.util.Optional<Recipe> getRecipeById(String recipeId) {
                return java.util.Optional.empty();
            }

            @Override
            public boolean delete(String username, String recipeId) {
                return false;
            }

            @Override
            public boolean hasSavedRecipes(String username) {
                return false;
            }
        };

        Recipe recipe = createRecipe("", "EmptyIdRecipe");

        RecordingPresenter presenter = new RecordingPresenter();
        SaveRecipeInteractor interactor =
                new SaveRecipeInteractor(userDAO, recipeDAO, presenter);

        SaveRecipeInputData input = new SaveRecipeInputData("alice", recipe);
        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("Recipe has no id and cannot be saved.",
                presenter.lastOutput.getMessage());
    }
}
