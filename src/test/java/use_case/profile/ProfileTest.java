package use_case.profile;

import data_access.InMemoryUserDataAccessObject;
import entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfileTest {

    @Test
    void successTest() {
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        User testUser = new User("Apple", "Red", 170, 60, "Peanuts", true);

        userRepository.save(testUser);

        ProfileOutputBoundary presenter = new ProfileOutputBoundary() {
            @Override
            public void updateProfile(ProfileOutputData out) {
                assertEquals("Apple", out.getUsername());
                assertEquals("Red", out.getPassword());
                assertEquals(170, out.getHeight());
                assertEquals(60, out.getWeight());
                assertEquals("Peanuts", out.getAllergies());
                assertTrue(out.isVegan());
            }
        };

        ProfileInteractor interactor = new ProfileInteractor(userRepository, presenter);
        ProfileInputData input = new ProfileInputData("Apple");

        interactor.loadProfile(input);
    }

    @Test
    void emptyUserTest() {
        InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();

        User testUser = new User("Xavier", "Bunny");

        userRepository.save(testUser);

        ProfileOutputBoundary presenter = new ProfileOutputBoundary() {
            @Override
            public void updateProfile(ProfileOutputData out) {
                assertEquals("None", out.getAllergies());
            }
        };

        ProfileInteractor interactor = new ProfileInteractor(userRepository, presenter);
        ProfileInputData input = new ProfileInputData("Xavier");

        interactor.loadProfile(input);
    }
}
