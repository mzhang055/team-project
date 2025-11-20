package use_case.goals;


import entities.Goal;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class GoalStore {
    private final Map<String, List<Goal>> data = new ConcurrentHashMap<>();

    public synchronized Goal save(Goal g) {
        data.computeIfAbsent(g.getUserId(), k -> new ArrayList<>())
                .removeIf(x -> x.getId().equals(g.getId()));
        data.get(g.getUserId()).add(g);
        data.get(g.getUserId()).sort(Comparator.comparing(Goal::getStartDate).reversed());
        return g;
    }

    public synchronized void delete(String userId, UUID id) {
        data.getOrDefault(userId, List.of()).removeIf(g -> g.getId().equals(id));
    }

    public synchronized Optional<Goal> get(String userId, UUID id) {
        return data.getOrDefault(userId, List.of())
                .stream().filter(g -> g.getId().equals(id)).findFirst();
    }

    public synchronized void remove(String userId, UUID id) {
        data.getOrDefault(userId, List.of()).removeIf(g -> g.getId().equals(id));
    }

    public List<Goal> history(String userId) {
        return List.copyOf(data.getOrDefault(userId, List.of()));
    }

    public Optional<Goal> activeOn(String userId, LocalDate date) {
        return data.getOrDefault(userId, List.of()).stream()
                .filter(g -> !g.getStartDate().isAfter(date) &&
                        (g.getEndDate() == null || !g.getEndDate().isBefore(date)))
                .findFirst();
    }

    public List<Goal> overlapping(String userId, LocalDate from, LocalDate to) {
        return data.getOrDefault(userId, List.of()).stream()
                .filter(g -> (g.getEndDate() == null || !g.getEndDate().isBefore(from))
                        && !g.getStartDate().isAfter(to))
                .collect(Collectors.toList());
    }
}
