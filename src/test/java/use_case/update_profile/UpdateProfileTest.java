package use_case.update_profile;

import data_access.InMemoryUserDataAccessObject;
import data_access.UserDataAccessInterface;
import entities.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UpdateProfileTest {

    @Test
    void updateProfileInteractorSuccessTest() {

        UserDataAccessInterface userDAO = new InMemoryUserDataAccessObject();
        User user = new User("Blue", "Berry");
        userDAO.save(user);

        UpdateProfileInputData input = new UpdateProfileInputData(
                "Blue",
                156,
                47,
                "Blueberries",
                true
        );

        UpdateProfileOutputBoundary presenter = new UpdateProfileOutputBoundary() {
            @Override
            public void prepareSuccessView(UpdateProfileOutputData data) {
                assertEquals("Blue", data.getUsername());
                assertEquals("Profile Updated", data.getMessage());

                User updated = userDAO.getUser("Blue");
                assertEquals(156, updated.getHeight());
                assertEquals(47, updated.getWeight());
                assertEquals("Blueberries", updated.getAllergies());
                assertTrue(updated.isVegan());
            }
        };

        UpdateProfileInputBoundary interactor = new UpdateProfileInteractor(userDAO, presenter);
        interactor.execute(input);

    }
}
