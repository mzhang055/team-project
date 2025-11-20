package use_case.goals;

import java.time.LocalDate;

public final class UpdateGoalRequest {
    private final Integer calories;
    private final Integer protein;
    private final Integer carbs;
    private final Integer fats;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public UpdateGoalRequest(
            Integer calories,
            Integer protein,
            Integer carbs,
            Integer fats,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer calories() { return calories; }
    public Integer protein() { return protein; }
    public Integer carbs() { return carbs; }
    public Integer fats() { return fats; }
    public LocalDate startDate() { return startDate; }
    public LocalDate endDate() { return endDate; }

    public void validate(LocalDate effectiveStart, LocalDate effectiveEnd) {
        if (effectiveEnd != null && effectiveStart.isAfter(effectiveEnd)) {
            throw new IllegalArgumentException("startDate must be on/before endDate");
        }
        if (calories != null && (calories < 0 || calories > 10_000)) {
            throw new IllegalArgumentException("calories out of range");
        }
        if (protein != null && (protein < 0 || protein > 1_000)) {
            throw new IllegalArgumentException("protein out of range");
        }
        if (carbs != null && (carbs < 0 || carbs > 1_000)) {
            throw new IllegalArgumentException("carbs out of range");
        }
        if (fats != null && (fats < 0 || fats > 1_000)) {
            throw new IllegalArgumentException("fats out of range");
        }
    }
}
