package use_case.goals;

import java.time.LocalDate;

public final class CreateGoalRequest {
    private final String userId;
    private final int calories;
    private final int protein;
    private final int carbs;
    private final int fats;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public CreateGoalRequest(
            String userId,
            int calories,
            int protein,
            int carbs,
            int fats,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this.userId = userId;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String userId() { return userId; }
    public int calories() { return calories; }
    public int protein() { return protein; }
    public int carbs() { return carbs; }
    public int fats() { return fats; }
    public LocalDate startDate() { return startDate; }
    public LocalDate endDate() { return endDate; }

    public void validate() {
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("userId must not be blank");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("startDate must not be null");
        }
        if (endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate must be on/before endDate");
        }
        if (calories < 0 || calories > 10_000) {
            throw new IllegalArgumentException("calories out of range");
        }
        if (protein < 0 || protein > 1_000) {
            throw new IllegalArgumentException("protein out of range");
        }
        if (carbs < 0 || carbs > 1_000) {
            throw new IllegalArgumentException("carbs out of range");
        }
        if (fats < 0 || fats > 1_000) {
            throw new IllegalArgumentException("fats out of range");
        }

        int calc = 4 * protein + 4 * carbs + 9 * fats;
        double tol = Math.max(100, calories * 0.15); // 100 kcal or 15%
        if (Math.abs(calc - calories) > tol) {
            throw new IllegalArgumentException(
                    "Macros (P/C/F) don't roughly match calories; got ~" + calc + " kcal from macros"
            );
        }
    }
}
