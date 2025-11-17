package use_case.update_profile;

public class UpdateProfileInputData {
    private final String username;
    private final double height;
    private final double weight;
    private final String allergies;
    private final boolean vegan;

    public UpdateProfileInputData(String username, double height, double weight,
                                  String allergies, boolean vegan) {
        this.username = username;
        this.height = height;
        this.weight = weight;
        this.allergies = allergies;
        this.vegan = vegan;
    }

    public String getUsername() { return username; }
    public double getHeight() { return height; }
    public double getWeight() { return weight; }
    public String getAllergies() { return allergies; }
    public boolean isVegan() { return vegan; }
}
