package nutrition.goals;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CreateGoalRequest(
    @NotBlank String userId,
    @Min(0) @Max(10000) int calories,
    @Min(0) @Max(1000) int protein,
    @Min(0) @Max(1000) int carbs,
    @Min(0) @Max(1000) int fats,
    @NotNull LocalDate startDate,
    LocalDate endDate
) {
  public void validate() {
    if (endDate != null && startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("startDate must be on/before endDate");
    }
    int calc = 4*protein + 4*carbs + 9*fats;
    double tol = Math.max(100, calories * 0.15);
  }
}
