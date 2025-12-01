package use_case.saved_recipe;

import data_access.UserDataAccessInterface;
import entities.Recipe;
import entities.User;
import org.junit.jupiter.api.Test;
import use_case.recipe_search.RecipeSearchDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for GetSavedRecipesInteractor.
 * This class is written without any mocking framework.
 * It aims to cover 100% of:
 *  - GetSavedRecipesInteractor
 *  - GetSavedRecipesInputData
 *  - GetSavedRecipesOutputData
 */
public class SavedRecipeTest {
    private static class RecordingPresenter implements GetSavedRecipesOutputBoundary {
        GetSavedRecipesOutputData lastOutput;

        @Override
        public void present(GetSavedRecipesOutputData outputData) {
            this.lastOutput = outputData;
        }
    }


    private Recipe createRecipe(String id, String name) {
        return new Recipe(id, name,
                "category", "area", "instructions", "imageUrl", null);
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

        RecipeSearchDataAccessInterface recipeSearchDAO = new RecipeSearchDataAccessInterface() {
            @Override
            public List<Recipe> searchRecipesByName(String name) {
                fail("searchRecipesByName should not be called when username is null");
                return Collections.emptyList();
            }

            @Override
            public Recipe getRecipeById(String id) {
                fail("getRecipeById should not be called when username is null");
                return null;
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        GetSavedRecipesInteractor interactor =
                new GetSavedRecipesInteractor(userDAO, recipeSearchDAO, presenter);

        GetSavedRecipesInputData input = new GetSavedRecipesInputData(null);
        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("No user logged in.", presenter.lastOutput.getMessage());
        assertTrue(presenter.lastOutput.getRecipes().isEmpty());
    }

    @Test
    void testBlankUsername() {
        UserDataAccessInterface userDAO = new UserDataAccessInterface() {
            @Override
            public boolean existsByUsername(String username) {
                fail("existsByUsername should not be called when username is blank");
                return false;
            }

            @Override
            public User getUser(String username) {
                fail("getUser should not be called when username is blank");
                return null;
            }

            @Override
            public void save(User user) {
                fail("save(User) should not be called when username is blank");
            }

            @Override
            public List<User> getAllUsers() {
                return Collections.emptyList();
            }
        };

        RecipeSearchDataAccessInterface recipeSearchDAO = new RecipeSearchDataAccessInterface() {
            @Override
            public List<Recipe> searchRecipesByName(String name) {
                fail("searchRecipesByName should not be called when username is blank");
                return Collections.emptyList();
            }

            @Override
            public Recipe getRecipeById(String id) {
                fail("getRecipeById should not be called when username is blank");
                return null;
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        GetSavedRecipesInteractor interactor =
                new GetSavedRecipesInteractor(userDAO, recipeSearchDAO, presenter);

        GetSavedRecipesInputData input = new GetSavedRecipesInputData("   ");
        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("No user logged in.", presenter.lastOutput.getMessage());
        assertTrue(presenter.lastOutput.getRecipes().isEmpty());
    }

    @Test
    void testUserDoesNotExist() {
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

        RecipeSearchDataAccessInterface recipeSearchDAO = new RecipeSearchDataAccessInterface() {
            @Override
            public List<Recipe> searchRecipesByName(String name) {
                fail("searchRecipesByName should not be called when user does not exist");
                return Collections.emptyList();
            }

            @Override
            public Recipe getRecipeById(String id) {
                fail("getRecipeById should not be called when user does not exist");
                return null;
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        GetSavedRecipesInteractor interactor =
                new GetSavedRecipesInteractor(userDAO, recipeSearchDAO, presenter);

        GetSavedRecipesInputData input = new GetSavedRecipesInputData("alice");
        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("User not found.", presenter.lastOutput.getMessage());
        assertTrue(presenter.lastOutput.getRecipes().isEmpty());
    }

    @Test
    void testUserNullFromDAO() {
        UserDataAccessInterface userDAO = new UserDataAccessInterface() {
            @Override
            public boolean existsByUsername(String username) {
                return true;
            }

            @Override
            public User getUser(String username) {
                return null;
            }

            @Override
            public void save(User user) {
                fail("save(User) should not be called when user is null");
            }

            @Override
            public List<User> getAllUsers() {
                return Collections.emptyList();
            }
        };

        RecipeSearchDataAccessInterface recipeSearchDAO = new RecipeSearchDataAccessInterface() {
            @Override
            public List<Recipe> searchRecipesByName(String name) {
                fail("searchRecipesByName should not be called when user is null");
                return Collections.emptyList();
            }

            @Override
            public Recipe getRecipeById(String id) {
                fail("getRecipeById should not be called when user is null");
                return null;
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        GetSavedRecipesInteractor interactor =
                new GetSavedRecipesInteractor(userDAO, recipeSearchDAO, presenter);

        GetSavedRecipesInputData input = new GetSavedRecipesInputData("bob");
        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("User not found.", presenter.lastOutput.getMessage());
        assertTrue(presenter.lastOutput.getRecipes().isEmpty());
    }

    @Test
    void testNoSavedRecipes() {
        User user = createUser("charlie"); // recipeIds list is empty

        UserDataAccessInterface userDAO = new UserDataAccessInterface() {
            @Override
            public boolean existsByUsername(String username) {
                return true;
            }

            @Override
            public User getUser(String username) {
                return user;
            }

            @Override
            public void save(User user) {
                // not used in this interactor
            }

            @Override
            public List<User> getAllUsers() {
                return Collections.emptyList();
            }
        };

        RecipeSearchDataAccessInterface recipeSearchDAO = new RecipeSearchDataAccessInterface() {
            @Override
            public List<Recipe> searchRecipesByName(String name) {
                fail("searchRecipesByName should not be called when there are no ids");
                return Collections.emptyList();
            }

            @Override
            public Recipe getRecipeById(String id) {
                fail("getRecipeById should not be called when there are no ids");
                return null;
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        GetSavedRecipesInteractor interactor =
                new GetSavedRecipesInteractor(userDAO, recipeSearchDAO, presenter);

        GetSavedRecipesInputData input = new GetSavedRecipesInputData("charlie");
        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertTrue(presenter.lastOutput.isSuccess());
        assertEquals("No recipes saved yet.", presenter.lastOutput.getMessage());
        assertTrue(presenter.lastOutput.getRecipes().isEmpty());
    }

    @Test
    void testAllInvalidRecipeIds() {
        User user = createUser("dave");
        // Add various invalid ids
        user.addRecipe(null);      // will hit id == null
        user.addRecipe("   ");     // will hit id.isBlank()
        user.addRecipe("io");      // will throw IOException
        user.addRecipe("null");    // will return null

        UserDataAccessInterface userDAO = new UserDataAccessInterface() {
            @Override
            public boolean existsByUsername(String username) {
                return true;
            }

            @Override
            public User getUser(String username) {
                return user;
            }

            @Override
            public void save(User user) {
                // not used
            }

            @Override
            public List<User> getAllUsers() {
                return Collections.emptyList();
            }
        };

        RecipeSearchDataAccessInterface recipeSearchDAO = new RecipeSearchDataAccessInterface() {
            @Override
            public List<Recipe> searchRecipesByName(String name) {
                fail("searchRecipesByName should not be called in this test");
                return Collections.emptyList();
            }

            @Override
            public Recipe getRecipeById(String id) throws IOException {
                if ("io".equals(id)) {
                    throw new IOException("simulated");
                }
                return null;
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        GetSavedRecipesInteractor interactor =
                new GetSavedRecipesInteractor(userDAO, recipeSearchDAO, presenter);

        GetSavedRecipesInputData input = new GetSavedRecipesInputData("dave");
        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertTrue(presenter.lastOutput.isSuccess());
        assertEquals("No valid recipes found.", presenter.lastOutput.getMessage());
        assertTrue(presenter.lastOutput.getRecipes().isEmpty());
    }

    @Test
    void testLoadedRecipes() {
        User user = createUser("emma");
        user.addRecipe("id1");
        user.addRecipe("id2");

        UserDataAccessInterface userDAO = new UserDataAccessInterface() {
            @Override
            public boolean existsByUsername(String username) {
                return true;
            }

            @Override
            public User getUser(String username) {
                return user;
            }

            @Override
            public void save(User user) {
                // not used
            }

            @Override
            public List<User> getAllUsers() {
                return Collections.emptyList();
            }
        };

        RecipeSearchDataAccessInterface recipeSearchDAO = new RecipeSearchDataAccessInterface() {
            @Override
            public List<Recipe> searchRecipesByName(String name) {
                fail("searchRecipesByName should not be called in this interactor");
                return Collections.emptyList();
            }

            @Override
            public Recipe getRecipeById(String id) {
                if ("id1".equals(id)) {
                    return createRecipe("id1", "Recipe 1");
                } else if ("id2".equals(id)) {
                    return createRecipe("id2", "Recipe 2");
                }
                return null;
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        GetSavedRecipesInteractor interactor =
                new GetSavedRecipesInteractor(userDAO, recipeSearchDAO, presenter);

        GetSavedRecipesInputData input = new GetSavedRecipesInputData("emma");
        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertTrue(presenter.lastOutput.isSuccess());
        assertEquals("Loaded recipes.", presenter.lastOutput.getMessage());
        List<Recipe> recipes = presenter.lastOutput.getRecipes();
        assertEquals(2, recipes.size());
        assertEquals("Recipe 1", recipes.get(0).getName());
        assertEquals("Recipe 2", recipes.get(1).getName());
    }

    @Test
    void testInputData() {
        GetSavedRecipesInputData input = new GetSavedRecipesInputData("tester");
        assertEquals("tester", input.getUsername());
    }

    @Test
    void testOutputDataConstructorWithNullRecipes() {
        GetSavedRecipesOutputData output =
                new GetSavedRecipesOutputData(true, "msg", null);

        assertTrue(output.isSuccess());
        assertEquals("msg", output.getMessage());
        assertNotNull(output.getRecipes());
        assertTrue(output.getRecipes().isEmpty());
    }

    @Test
    void testOutputDataConstructorWithNonNullRecipes() {
        List<Recipe> list = new ArrayList<>();
        list.add(createRecipe("id", "Name"));

        GetSavedRecipesOutputData output =
                new GetSavedRecipesOutputData(false, "msg2", list);

        assertFalse(output.isSuccess());
        assertEquals("msg2", output.getMessage());
        assertEquals(1, output.getRecipes().size());
        assertEquals("Name", output.getRecipes().get(0).getName());

        list.clear();
        assertEquals(1, output.getRecipes().size());
    }
}
