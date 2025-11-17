package use_case.set_edit_goals;

import java.time.LocalDate;

public record UpdateGoalRequest(
        Integer calories,
        Integer protein,
        Integer carbs,
        Integer fats,
        LocalDate startDate,
        LocalDate endDate
) {
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
