package use_case.set_edit_goals;

import entities.GoalService;
import use_case.set_edit_goals.*;
import java.time.LocalDate;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        // Pure Java wiring (no Spring): create store + service
        GoalStore store = new GoalStore();
        GoalService service = new GoalService(store);

        // 1) Create u1
        CreateGoalRequest create = new CreateGoalRequest(
                "u1", 2300, 160, 220, 70, LocalDate.parse("2025-11-16"), null);
        Goal created = service.create(create);
        System.out.println("Created: " + GoalResponse.of(created));

        // 2) Edit (PATCH-like): update calories + protein
        UpdateGoalRequest edit = new UpdateGoalRequest(2100, 150, null, null, null, null);
        Goal updated = service.update(created.getId(), "u1", edit);
        System.out.println("Updated: " + GoalResponse.of(updated));

        // 3) Show active and history
        System.out.println("Active: " + service.activeOn("u1", LocalDate.now())
                .map(GoalResponse::of).orElse(null));
        System.out.println("History: " + service.history("u1").stream()
                .map(GoalResponse::of).toList());
    }
}
