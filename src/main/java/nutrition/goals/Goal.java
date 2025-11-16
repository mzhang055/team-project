package nutrition.goals;

import java.time.LocalDate;
import java.util.UUID;

public class Goal {
  private UUID id = UUID.randomUUID();
  private String userId;
  private int calories, protein, carbs, fats;
  private LocalDate startDate; // inclusive
  private LocalDate endDate;   // inclusive; null = open-ended

  public UUID getId() { return id; }
  public String getUserId() { return userId; }
  public void setUserId(String userId) { this.userId = userId; }
  public int getCalories() { return calories; }
  public void setCalories(int calories) { this.calories = calories; }
  public int getProtein() { return protein; }
  public void setProtein(int protein) { this.protein = protein; }
  public int getCarbs() { return carbs; }
  public void setCarbs(int carbs) { this.carbs = carbs; }
  public int getFats() { return fats; }
  public void setFats(int fats) { this.fats = fats; }
  public LocalDate getStartDate() { return startDate; }
  public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
  public LocalDate getEndDate() { return endDate; }
  public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}
