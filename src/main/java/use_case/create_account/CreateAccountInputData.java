package use_case.create_account;

public class CreateAccountInputData {
    private final String username;
    private final String password;

    private final double height;
    private final double weight;
    private final String allergies;
    private final boolean vegan;

    public CreateAccountInputData(String username, String password, double height,
                                  double weight, String allergies, boolean vegan) {
        this.username = username;
        this.password = password;
        this.height = height;
        this.weight = weight;
        this.allergies = allergies;
        this.vegan = vegan;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public double getHeight() {return height; }
    public double getWeight() {return weight; }
    public String getAllergies() {return allergies; }
    public boolean isVegan() {return vegan; }
}
