package use_case.goals;

import entities.Goal;

import java.time.LocalDate;
import java.util.UUID;

public final class GoalResponse {
    private final UUID id;
    private final String userId;
    private final int calories;
    private final int protein;
    private final int carbs;
    private final int fats;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public GoalResponse(
            UUID id,
            String userId,
            int calories,
            int protein,
            int carbs,
            int fats,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this.id = id;
        this.userId = userId;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static GoalResponse of(Goal g) {
        return new GoalResponse(
                g.getId(),
                g.getUserId(),
                g.getCalories(),
                g.getProtein(),
                g.getCarbs(),
                g.getFats(),
                g.getStartDate(),
                g.getEndDate()
        );
    }

    public UUID id() { return id; }
    public String userId() { return userId; }
    public int calories() { return calories; }
    public int protein() { return protein; }
    public int carbs() { return carbs; }
    public int fats() { return fats; }
    public LocalDate startDate() { return startDate; }
    public LocalDate endDate() { return endDate; }
}
