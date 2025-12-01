package data_access;

import entities.Meal;
import java.util.*;
import java.io.*;

/**
 * In-memory implementation of MealDataAccessInterface.
 * Stores meals in memory using a HashMap.
 */
public class InMemoryMealDataAccessObject implements MealDataAccessInterface {

    private final Map<String, Meal> meals = new HashMap<>();
    private final File storageFile;

    public InMemoryMealDataAccessObject() {
        this("meals.ser");
    }

    public InMemoryMealDataAccessObject(String filePath) {
        this.storageFile = new File(filePath);
        loadFromFile();
    }

    @Override
    public synchronized boolean save(Meal meal) {
        meals.put(meal.getId(), meal);
        saveToFile();
        return true;
    }

    @Override
    public synchronized List<Meal> getMealsByUserId(String userId) {
        List<Meal> result = new ArrayList<>();
        for (Meal meal : meals.values()) {
            if (meal.getUserId().equals(userId)) {
                result.add(meal);
            }
        }
        return result;
    }

    public boolean update(Meal updatedMeal) {
        String id = updatedMeal.getId();
        if (!meals.containsKey(id)) {
            return false;
        }
        meals.put(id, updatedMeal);
        return true;
    }

    public boolean delete(String mealId) {
        Meal removed = meals.remove(mealId);
        if (removed == null) {
            return false;
        }
        return true;
    }

    private void saveToFile() {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(storageFile))) {
            out.writeObject(meals);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        if (!storageFile.exists()) {
            return;
        }

        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(storageFile))) {
            Object obj = in.readObject();
            if (obj instanceof Map) {
                meals.clear();
                meals.putAll((Map<String, Meal>) obj);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}