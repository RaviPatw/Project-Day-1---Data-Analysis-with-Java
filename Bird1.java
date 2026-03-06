public class Bird {
    private String name;
    private String diet;
    private String conservationStatus;
    
    public Bird(String name, String diet, String conservationStatus) {
        this.name = name;
        this.diet = diet;
        this.conservationStatus = conservationStatus;
    }
    public String getName() {
        return name;
    }
    public String getDiet() {
        return diet;
    }
    public String getConservationStatus() {
        return conservationStatus;
    }

    public String setDiet(String d){
        diet=d;
    }
    
    public boolean isCarnivore() {
        return diet.toLowerCase().contains("carnivore");
    }
    public boolean isHerbivore() {
        return diet.toLowerCase().contains("herbivore");
    }
    public boolean isOmnivore() {
        return diet.toLowerCase().contains("omnivore");
    }
    public boolean isEndangered() {
        return conservationStatus.equals("Endangered");
    }
    public boolean isVulnerable() {
        return conservationStatus.equals("Vulnerable");
    }
    public boolean isLeastConcern() {
        return conservationStatus.equals("Least Concern");
    }
    
    @Override
    public String toString() {
        return name + " - Diet: " + diet + ", Status: " + conservationStatus;
    }
}