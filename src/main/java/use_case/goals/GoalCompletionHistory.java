package use_case.goals;

import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class GoalCompletionHistory {

    private static final Map<LocalDate, Boolean> completionByDate = new LinkedHashMap<>();

    public static void markCompletion(LocalDate date, boolean done) {
        completionByDate.put(date, done);
    }

    public static void markToday(boolean done) {
        markCompletion(LocalDate.now(), done);
    }

    public static Boolean isCompleted(LocalDate date) {
        return completionByDate.get(date);
    }

    public static Map<LocalDate, Boolean> getAllRecords() {
        return Collections.unmodifiableMap(completionByDate);
    }

    public static int getCurrentStreak() {
        int streak = 0;
        LocalDate today = LocalDate.now();
        LocalDate current = today;
        while (Boolean.TRUE.equals(completionByDate.get(current))) {
            streak++;
            current = current.minusDays(1);
        }
        return streak;
    }
}
