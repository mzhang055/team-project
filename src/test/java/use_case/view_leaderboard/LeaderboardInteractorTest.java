package use_case.view_leaderboard;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.List;
import java.util.Map;


class LeaderboardInteractorTest {

    @Test
    void successtest() {

        MockPresenter presenter = new MockPresenter();
        MockDataAccess dataAccess = new MockDataAccess();
        dataAccess.mockData = Arrays.asList(
                Map.entry("user1", 100),
                Map.entry("user2", 80),
                Map.entry("user3", 60)
        );

        LeaderboardInteractor interactor = new LeaderboardInteractor(presenter, dataAccess);
        interactor.getLeaderboard("testUser");

        assertTrue(presenter.successCalled);
        assertEquals(Arrays.asList("user1", "user2", "user3"), presenter.rankedUsernames);
        assertEquals(Arrays.asList(100, 80, 60), presenter.scores);
    }

    @Test
    void failTest_EmptyUsername() {
        MockPresenter presenter = new MockPresenter();
        MockDataAccess dataAccess = new MockDataAccess();

        LeaderboardInteractor interactor = new LeaderboardInteractor(presenter, dataAccess);
        interactor.getLeaderboard("");

        assertTrue(presenter.failCalled);
        assertEquals("Please enter a valid username!", presenter.errorMessage);
    }

    @Test
    void failTest_NullUsername() {
        MockPresenter presenter = new MockPresenter();
        MockDataAccess dataAccess = new MockDataAccess();

        LeaderboardInteractor interactor = new LeaderboardInteractor(presenter, dataAccess);
        interactor.getLeaderboard(null);

        assertTrue(presenter.failCalled);
        assertEquals("Please enter a valid username!", presenter.errorMessage);
    }

    @Test
    void failTest_DataAccessException() {
        MockPresenter presenter = new MockPresenter();
        MockDataAccess dataAccess = new MockDataAccess();
        dataAccess.throwException = true;

        LeaderboardInteractor interactor = new LeaderboardInteractor(presenter, dataAccess);
        interactor.getLeaderboard("testUser");

        assertTrue(presenter.failCalled);
        assertTrue(presenter.errorMessage.contains("Error retrieving leaderboard"));
    }

    @Test
    void testEmptyLeaderboard() {
        MockPresenter presenter = new MockPresenter();
        MockDataAccess dataAccess = new MockDataAccess();
        dataAccess.mockData = new ArrayList<>();

        LeaderboardInteractor interactor = new LeaderboardInteractor(presenter, dataAccess);
        interactor.getLeaderboard("testUser");

        assertTrue(presenter.successCalled);
        assertTrue(presenter.rankedUsernames.isEmpty());
        assertTrue(presenter.scores.isEmpty());
    }

    static class MockPresenter implements LeaderboardOutputBoundary {
        boolean successCalled = false;
        boolean failCalled = false;
        List<String> rankedUsernames;
        List<Integer> scores;
        String errorMessage;

        public void prepareSuccessView(LeaderboardOutputData data) {
            successCalled = true;
            rankedUsernames = data.getRankedUsernames();
            scores = data.getScores();
        }

        public void prepareFailView(String error) {
            failCalled = true;
            errorMessage = error;
        }
    }

    static class MockDataAccess implements LeaderboardDataAccessInterface{
        List<Map.Entry<String, Integer>> mockData = new ArrayList<>();
        boolean throwException = false;

        public List<Map.Entry<String, Integer>> getSortedLeaderboard() {
            if (throwException) throw new RuntimeException("Test exception");
                return mockData;
        }

        public Map<String, Integer> getLeaderboardData() {
            Map<String, Integer> result = new HashMap<>();
            for (Map.Entry<String, Integer> entry : mockData) {
                result.put(entry.getKey(), entry.getValue());
            }
            return result;
        }
    }
}
