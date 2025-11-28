package use_case.goals;

import java.time.LocalDate;
import java.util.*;

public class UserManager {
    private static final List<String> registeredUsers = new ArrayList<>();

    static {
        registeredUsers.add("amanda");
        registeredUsers.add("christina");
        registeredUsers.add("david");
        registeredUsers.add("michelle");
        registeredUsers.add("aykan");
        registeredUsers.add("mandy");
    }

    public static List<String> getAllUsers() {
        return new ArrayList<>(registeredUsers);
    }

    public static void registerUser(String username) {
        if(registeredUsers.contains(username)){
            registeredUsers.add(username);
        }
    }

    public static void initializeTestData() {
        Random random = new Random();
        for(String user : registeredUsers){
            for(int i = 0; i < random.nextInt(20) + 5; i++){
            LocalDate date = LocalDate.now().minusDays(i);
            GoalCompletionHistory.markCompletion(user, date, random.nextBoolean());
            }
        }
    }
}

