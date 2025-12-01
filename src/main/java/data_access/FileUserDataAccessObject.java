package data_access;

import entities.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.ArrayList;

public class FileUserDataAccessObject implements UserDataAccessInterface {
    private static final String username   = "username";
    private static final String password   = "password";
    private static final String height     = "height";
    private static final String weight     = "weight";
    private static final String allergies  = "allergies";
    private static final String vegan      = "vegan";
    private static final String friends    = "friends";
    private static final String incoming   = "incomingRequests";
    private static final String outgoing   = "outgoingRequests";
    private static final String meals = "mealIds";
    private static final String recipes = "recipeIds";
    private final File file;
    private final Map<String, User> users = new HashMap<>();

    public FileUserDataAccessObject(String filePath) {
        this.file = new File(filePath);
        if (file.exists()) {
            readFromFile();
        } else {
            writeToFile();
        }
    }

    public synchronized boolean existsByUsername(String username) {
        return  users.containsKey(username);
    }

    public synchronized User getUser(String username) {
        return users.get(username);
    }

    public synchronized void save(User user) {
        users.put(user.getUsername(), user);
        writeToFile();
    }

    private void readFromFile() {
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(new FileInputStream(file),
                StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            if (sb.length() == 0) {
                return;
            }
            JSONObject root = new JSONObject(sb.toString());
            for (String username : root.keySet()) {
                JSONObject userJson = root.getJSONObject(username);
                User user = jsonToUser(userJson);
                users.put(username, user);
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException("Failed to read users from file", e);
        }
    }

    private void writeToFile() {
        JSONObject root = new JSONObject();
        try {
            for (User user : users.values()) {
                JSONObject userJson = userToJson(user);
                root.put(user.getUsername(), userJson);
            }
        } catch (JSONException e) {
            throw new RuntimeException("Failed to serialize users to JSON", e);
        }

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            writer.write(root.toString(2));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write users to file", e);
        }
    }

    private JSONObject userToJson(User user) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put(username, user.getUsername());
        obj.put(password, user.getPassword());
        obj.put(height, user.getHeight());
        obj.put(weight, user.getWeight());
        obj.put(allergies, user.getAllergies());
        obj.put(vegan, user.isVegan());

        JSONArray friendsArray = new JSONArray();
        for (String f : user.getFriendsUsernames()) {
            friendsArray.put(f);
        }
        obj.put(friends, friendsArray);

        JSONArray incomingArray = new JSONArray();
        for (String r : user.getIncomingFriendRequests()) {
            incomingArray.put(r);
        }
        obj.put(incoming, incomingArray);

        JSONArray outgoingArray = new JSONArray();
        for (String r : user.getOutgoingFriendRequests()) {
            outgoingArray.put(r);
        }
        obj.put(outgoing, outgoingArray);

        JSONArray mealArray = new JSONArray();
        for (String id : user.getMealIds()) {
            mealArray.put(id);
        }
        obj.put(meals, mealArray);

        JSONArray recipeArray = new JSONArray();
        for (String id : user.getRecipeIds()) {
            recipeArray.put(id);
        }
        obj.put(recipes, recipeArray);

        return obj;
    }

    private User jsonToUser(JSONObject obj) throws JSONException {
        String username0 = obj.getString(username);
        String password0 = obj.getString(password);
        double height0 = obj.optDouble(height, 0.0);
        double weight0 = obj.optDouble(weight, 0.0);
        String allergies0 = obj.optString(allergies, "");
        boolean vegan0 = obj.optBoolean(vegan, false);

        User user = new User(username0, password0, height0, weight0, allergies0, vegan0);

        if (obj.has(friends)) {
            JSONArray friendsArray = obj.getJSONArray(friends);
            for (int i = 0; i < friendsArray.length(); i++) {
                user.getFriendsUsernames().add(friendsArray.getString(i));
            }
        }

        if (obj.has(incoming)) {
            JSONArray incomingArray = obj.getJSONArray(incoming);
            for (int i = 0; i < incomingArray.length(); i++) {
                user.getIncomingFriendRequests().add(incomingArray.getString(i));
            }
        }

        if (obj.has(outgoing)) {
            JSONArray outgoingArray = obj.getJSONArray(outgoing);
            for (int i = 0; i < outgoingArray.length(); i++) {
                user.getOutgoingFriendRequests().add(outgoingArray.getString(i));
            }
        }

        if (obj.has(meals)) {
            JSONArray mealArray = obj.getJSONArray(meals);
            for (int i = 0; i < mealArray.length(); i++) {
                user.getMealIds().add(mealArray.getString(i));
            }
        }

        if (obj.has(recipes)) {
            JSONArray recipeArray = obj.getJSONArray(recipes);
            for (int i = 0; i < recipeArray.length(); i++) {
                user.getRecipeIds().add(recipeArray.getString(i));
            }
        }

        return user;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
