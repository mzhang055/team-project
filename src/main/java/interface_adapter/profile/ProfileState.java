package interface_adapter.profile;

public class ProfileState {
    private String username;
    private String password;
    private double height;
    private double weight;
    private String allergies;
    private boolean vegan;

    public ProfileState() {}

    public ProfileState(ProfileState other) {
        this.username = other.username;
        this.password = other.password;
        this.height = other.height;
        this.weight = other.weight;
        this.allergies = other.allergies;
        this.vegan = other.vegan;
    }

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public double getHeight() {return height;}
    public void setHeight(double height) {this.height = height;}

    public double getWeight() {return weight;}
    public void setWeight(double weight) {this.weight = weight;}

    public String getAllergies() {return allergies;}
    public void setAllergies(String allergies) {this.allergies = allergies;}

    public boolean getVegan() {return vegan;}
    public void setVegan(boolean vegan) {this.vegan = vegan;}
}
