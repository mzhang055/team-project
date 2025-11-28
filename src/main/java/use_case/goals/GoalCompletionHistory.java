package use_case.goals;

import java.time.LocalDate;
import java.util.Collections;
import java.util.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GoalCompletionHistory {

    private static final Map<String, Map<LocalDate, Boolean>> userCompletions = new ConcurrentHashMap<>();

    public static void markCompletion(String userId, LocalDate date, boolean done)
    {
        userCompletions.computeIfAbsent(userId, k -> new ConcurrentHashMap<>())
                .put(date, done);
    }

    public static void markToday(String userId, boolean done) {
        markCompletion(userId, LocalDate.now(), done);
    }

    public static Boolean isCompleted(String userId, LocalDate date) {

        return userCompletions.getOrDefault(userId, Collections.emptyMap()).get(date);

    }

    public static int getUserCompletedDays(String userId) {
        Map<LocalDate, Boolean> userRecords = userCompletions.getOrDefault(userId, Collections.emptyMap());
        return (int) userRecords.values().stream()
                .filter(Boolean::booleanValue)
                .count();
    }

    public static Map<String, Integer> getAllRecords() {
        Map<String, Integer> scores = new HashMap<>();
        for (String userId : userCompletions.keySet()) {
            scores.put(userId, getUserCompletedDays(userId));
        }
        return scores;
    }

    public static Map <LocalDate, Boolean> getAllRecords(String userId){
        Map<LocalDate, Boolean> userRecords = userCompletions.getOrDefault(userId, Collections.emptyMap());
        return Collections.unmodifiableMap(new LinkedHashMap<>(userRecords));
    }

    public static void markToday(boolean done) {
        markToday("defaultUser", done);
    }

    public  static Boolean isCompleted(LocalDate date){
        return isCompleted("defaultUser", date);
    }

    public static int getCurrentStreak() {
        return getCurrentStreak("defaultUser");
    }

    public static int getCurrentStreak(String userId) {
        int Streak = 0;
        LocalDate current = LocalDate.now();
        Map<LocalDate, Boolean> userRecords = userCompletions.getOrDefault(userId, Collections.emptyMap());

        while(Boolean.TRUE.equals(userRecords.get(current))){
            Streak++;
            current = current.minusDays(1);
        }
        return Streak;
    }

    public static Map<LocalDate, Boolean> getALlRecords() {
        return getAllRecords("defaultUser");
    }
}
