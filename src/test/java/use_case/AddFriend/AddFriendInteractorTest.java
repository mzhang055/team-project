package use_case.AddFriend;

import data_access.InMemoryUserDataAccessObject;
import data_access.UserDataAccessInterface;
import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.add_friend.AddFriendInputData;
import use_case.add_friend.AddFriendInteractor;
import use_case.add_friend.AddFriendOutputBoundary;
import use_case.add_friend.AddFriendOutputData;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AddFriendInteractorTest {
    private UserDataAccessInterface userDataAccess;
    private TestAddFriendPresenter presenter;
    private AddFriendInteractor interactor;

    private static class TestAddFriendPresenter implements AddFriendOutputBoundary {
        AddFriendOutputData successData;
        String failMessage;
        boolean successCalled = false;
        boolean failCalled = false;

        public void prepareSuccessView(AddFriendOutputData outputData) {
            successCalled = true;
            successData = outputData;
        }

        public void prepareFailView(String errorMessage) {
            failCalled = true;
            failMessage = errorMessage;
        }
    }

    @BeforeEach
    void setUp(){
        userDataAccess = new InMemoryUserDataAccessObject();
        presenter = new TestAddFriendPresenter();
        interactor = new AddFriendInteractor(userDataAccess, presenter);
        userDataAccess.save(new User("Enna",
                "tskr", 160.0, 50.0, "milk", false));
        userDataAccess.save(new User("Elira",
                "nice", 170.0, 60.0, "", false));
    }

    @Test
    void success() {
        AddFriendInputData inputData =
                new AddFriendInputData("Enna", "Elira");
        interactor.execute(inputData);

        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);

        User Enna = userDataAccess.getUser("Enna");
        User Elira = userDataAccess.getUser("Elira");
        List<String> outgoing = Enna.getOutgoingFriendRequests();
        List<String> incoming = Elira.getIncomingFriendRequests();

        assertTrue(outgoing.contains("Elira"));
        assertTrue(incoming.contains("Enna"));
    }

    @Test
    void failWhenTargetUserDoesNotExist() {
        AddFriendInputData inputData = new AddFriendInputData(
                "Enna", "Luca");
        interactor.execute(inputData);

        assertFalse(presenter.successCalled);
        assertTrue(presenter.failCalled);
        assertNotNull(presenter.failMessage);
    }

    @Test
    void failWhenAddingSelf() {
        AddFriendInputData inputData =
                new AddFriendInputData("Enna", "Enna");

        interactor.execute(inputData);

        assertFalse(presenter.successCalled);
        assertTrue(presenter.failCalled);
        assertEquals("You cannot add yourself", presenter.failMessage);
    }

    @Test
    void failWhenAlreadyFriends() {
        // make them already friends
        User enna = userDataAccess.getUser("Enna");
        User elira = userDataAccess.getUser("Elira");
        enna.addFriend("Elira");
        elira.addFriend("Enna");
        userDataAccess.save(enna);
        userDataAccess.save(elira);

        AddFriendInputData inputData =
                new AddFriendInputData("Enna", "Elira");
        interactor.execute(inputData);

        assertFalse(presenter.successCalled);
        assertTrue(presenter.failCalled);
        assertEquals("You are already friends", presenter.failMessage);
    }

    @Test
    void failWhenRequestAlreadySent() {
        // simulate an existing pending request
        User enna = userDataAccess.getUser("Enna");
        User elira = userDataAccess.getUser("Elira");
        enna.getOutgoingFriendRequests().add("Elira");
        elira.getIncomingFriendRequests().add("Enna");
        userDataAccess.save(enna);
        userDataAccess.save(elira);

        AddFriendInputData inputData =
                new AddFriendInputData("Enna", "Elira");
        interactor.execute(inputData);

        assertFalse(presenter.successCalled);
        assertTrue(presenter.failCalled);
        assertEquals("Friend request already sent", presenter.failMessage);
    }

}
