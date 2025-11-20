package use_case.goals;

import entities.Goal;

@FunctionalInterface
public interface GoalCreatedListener {
    void onGoalCreated(Goal goal);
}
