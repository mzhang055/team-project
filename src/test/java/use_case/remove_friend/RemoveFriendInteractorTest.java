package use_case.remove_friend;

import data_access.UserDataAccessInterface;
import entities.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashSet;

public class RemoveFriendInteractorTest {

    @Test
    void testRemoveFriendSuccess() {
        MockUserDataAccess dataAccess = new MockUserDataAccess();
        MockPresenter presenter = new MockPresenter();
        RemoveFriendInteractor interactor = new RemoveFriendInteractor(dataAccess, presenter);

        User currentUser = new User("user1", "pass");
        currentUser.addFriend("user2");
        User friendUser = new User("user2", "pass");
        friendUser.addFriend("user1");

        dataAccess.addUser(currentUser);
        dataAccess.addUser(friendUser);

        interactor.execute(new RemoveFriendInputData("user1", "user2"));
        assertTrue(presenter.successCalled);
        assertEquals("user2", presenter.outputData.getRemovedFriendUsername());
    }

    @Test
    void testRemoveNonExistentUser(){
        MockUserDataAccess dataAccess = new MockUserDataAccess();
        MockPresenter presenter = new MockPresenter();
        RemoveFriendInteractor interactor = new RemoveFriendInteractor(dataAccess, presenter);

        User currentUser = new User("user1", "pass");
        dataAccess.addUser(currentUser);

        interactor.execute(new RemoveFriendInputData("user1", "nonExistent"));

        assertTrue(presenter.failCalled);
        assertTrue(presenter.errorMessage.contains("does not exist"));
    }

    @Test
    void testRemoveNonFriend() {
        MockUserDataAccess dataAccess = new MockUserDataAccess();
        MockPresenter presenter = new MockPresenter();
        RemoveFriendInteractor interactor = new RemoveFriendInteractor(dataAccess, presenter);

        User user1 = new User("user1", "pass");
        User user2 = new User("user2", "pass");
        dataAccess.addUser(user1);
        dataAccess.addUser(user2);

        interactor.execute(new RemoveFriendInputData("user1", "user2"));

        assertTrue(presenter.failCalled);
        assertTrue(presenter.errorMessage.contains("is not friend"));

    }

    @Test
    void testExceptionHandling() {
        MockUserDataAccess dataAccess = new MockUserDataAccess();
        MockPresenter presenter = new MockPresenter();
        RemoveFriendInteractor interactor = new RemoveFriendInteractor(dataAccess, presenter);

        dataAccess.throwException = true;
        interactor.execute(new RemoveFriendInputData("user1", "user2"));
        assertTrue(presenter.failCalled);
        assertTrue(presenter.errorMessage.contains("Failed to remove friend"));
    }

    static class MockUserDataAccess implements UserDataAccessInterface {
        private java.util.HashMap<String, User> users = new java.util.HashMap<>();
        public boolean throwException = false;

        public void addUser(User user) {
            users.put(user.getUsername(), user);
        }

        public boolean existsByUsername(String username){
            if (throwException) throw new RuntimeException("Test exception");
            return users.containsKey(username);
        }

        public User getUser(String username) {
            if (throwException) throw new RuntimeException("Test exception");
            return users.get(username);
        }

        public void save(User user) {
            if (throwException) throw new RuntimeException("Test exception");
            users.put(user.getUsername(), user);
        }
        public List<User> getAllUsers() {
            return new ArrayList<>(users.values());
        }
    }

    static class MockPresenter implements RemoveFriendOutputBoundary {
        boolean successCalled = false;
        boolean failCalled = false;
        RemoveFriendOutputData outputData;
        String errorMessage;

        public void prepareSuccessView(RemoveFriendOutputData outputData) {
            successCalled = true;
            this.outputData = outputData;
        }

        public void prepareFailView(String error) {
            failCalled = true;
            this.errorMessage = error;
        }
    }
}
