import data_access.InMemoryUserDataAccessObject;
import data_access.UserDataAccessInterface;
import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.accept_friend.AcceptFriendRequestInputData;
import use_case.accept_friend.AcceptFriendRequestInteractor;
import use_case.accept_friend.AcceptFriendRequestOutputBoundary;
import use_case.accept_friend.AcceptFriendRequestOutputData;
import use_case.add_friend.AddFriendInputData;
import use_case.add_friend.AddFriendOutputBoundary;
import use_case.add_friend.AddFriendOutputData;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AcceptFriendInteractorTest {
    private UserDataAccessInterface userDataAccess;
    private TestAcceptPresenter presenter;
    private AcceptFriendRequestInteractor interactor;

    private static class TestAcceptPresenter implements AcceptFriendRequestOutputBoundary {
        AcceptFriendRequestOutputData successData;
        String failMessage;
        boolean successCalled = false;
        boolean failCalled = false;

        public void prepareSuccessView(AcceptFriendRequestOutputData outputData) {
            successCalled = true;
            successData = outputData;
        }
        public void prepareFailView(String errorMessage) {
            failCalled = true;
            failMessage = errorMessage;
        }
    }

    @BeforeEach
    void setUp() {
        userDataAccess = new InMemoryUserDataAccessObject();
        presenter = new TestAcceptPresenter();
        interactor = new AcceptFriendRequestInteractor(userDataAccess, presenter);

        User Enna = new User("Enna",
                "tskr", 160.0, 50.0, "milk", false);
        User Elira = new User("Elira",
                "nice", 170.0, 60.0, "", false);
        Enna.getOutgoingFriendRequests().add("Elira");
        Elira.getIncomingFriendRequests().add("Enna");
        userDataAccess.save(Enna);
        userDataAccess.save(Elira);
    }

    @Test
    void success() {
        AcceptFriendRequestInputData inputData = new AcceptFriendRequestInputData(
                "Elira", "Enna");
        interactor.execute(inputData);

        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);

        User Enna = userDataAccess.getUser("Enna");
        User Elira = userDataAccess.getUser("Elira");
        assertFalse(Enna.getOutgoingFriendRequests().contains("Elira"));
        assertFalse(Elira.getIncomingFriendRequests().contains("Enna"));

        List<String> EnnaFriends = Enna.getFriendsUsernames();
        List<String> EliraFriends = Elira.getFriendsUsernames();
        assertTrue(EnnaFriends.contains("Elira"));
        assertTrue(EliraFriends.contains("Enna"));
    }

    @Test
    void failWhenNoRequest() {
        User Elira = userDataAccess.getUser("Elira");
        Elira.getIncomingFriendRequests().clear();
        userDataAccess.save(Elira);

        AcceptFriendRequestInputData inputData = new AcceptFriendRequestInputData(
                "Elira", "Enna");
        interactor.execute(inputData);

        assertFalse(presenter.successCalled);
        assertTrue(presenter.failCalled);
        assertNotNull(presenter.failMessage);
    }
}
