package nutrition.goals;

import java.time.LocalDate;
import java.util.UUID;

public record GoalResponse(
    UUID id, String userId, int calories, int protein, int carbs, int fats,
    LocalDate startDate, LocalDate endDate
) {
  public static GoalResponse of(Goal g) {
    return new GoalResponse(g.getId(), g.getUserId(), g.getCalories(),
        g.getProtein(), g.getCarbs(), g.getFats(), g.getStartDate(), g.getEndDate());
  }
}
