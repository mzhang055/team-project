package nutrition.goals;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class GoalService {
    private final GoalStore store;

    public GoalService(GoalStore store) { this.store = store; }

    /** Create a new goal; trims any previous open-ended goal to start-1, blocks true overlaps. */
    public Goal create(CreateGoalRequest req) {
        req.validate();
        LocalDate start = req.startDate();
        LocalDate end = req.endDate();
        LocalDate to = (end == null) ? LocalDate.of(9999, 12, 31) : end;

        for (Goal ex : store.overlapping(req.userId(), start, to)) {
            if (!ex.getStartDate().isAfter(start)) {
                LocalDate newEnd = start.minusDays(1);
                if (ex.getEndDate() == null || ex.getEndDate().isAfter(newEnd)) {
                    ex.setEndDate(newEnd);
                    store.save(ex);
                }
            } else {
                if (end == null || !end.isBefore(ex.getStartDate())) {
                    throw new IllegalStateException("New goal overlaps existing starting " + ex.getStartDate());
                }
            }
        }

        Goal g = new Goal();
        g.setUserId(req.userId());
        g.setCalories(req.calories());
        g.setProtein(req.protein());
        g.setCarbs(req.carbs());
        g.setFats(req.fats());
        g.setStartDate(start);
        g.setEndDate(end);
        return store.save(g);
    }

    public Goal update(UUID id, String userId, UpdateGoalRequest req) {
        Goal current = store.get(userId, id).orElseThrow(() -> new NoSuchElementException("goal not found"));

        int calories = (req.calories() != null) ? req.calories() : current.getCalories();
        int protein  = (req.protein()  != null) ? req.protein()  : current.getProtein();
        int carbs    = (req.carbs()    != null) ? req.carbs()    : current.getCarbs();
        int fats     = (req.fats()     != null) ? req.fats()     : current.getFats();
        LocalDate start = (req.startDate() != null) ? req.startDate() : current.getStartDate();
        LocalDate end   = (req.endDate()   != null) ? req.endDate()   : current.getEndDate();

        req.validate(start, end);

        store.remove(userId, id);

        LocalDate to = (end == null) ? LocalDate.of(9999, 12, 31) : end;
        for (Goal ex : store.overlapping(userId, start, to)) {
            if (!ex.getStartDate().isAfter(start)) {
                LocalDate newEnd = start.minusDays(1);
                if (ex.getEndDate() == null || ex.getEndDate().isAfter(newEnd)) {
                    ex.setEndDate(newEnd);
                    store.save(ex);
                }
            } else {
                if (end == null || !end.isBefore(ex.getStartDate())) {
                    store.save(current);
                    throw new IllegalStateException("Updated goal would overlap existing starting " + ex.getStartDate());
                }
            }
        }

        current.setCalories(calories);
        current.setProtein(protein);
        current.setCarbs(carbs);
        current.setFats(fats);
        current.setStartDate(start);
        current.setEndDate(end);
        return store.save(current);
    }

    public Optional<Goal> activeOn(String userId, LocalDate date) { return store.activeOn(userId, date); }

    public List<Goal> history(String userId) { return store.history(userId); }

    public void delete(UUID id, String userId) { store.delete(userId, id); }
}
