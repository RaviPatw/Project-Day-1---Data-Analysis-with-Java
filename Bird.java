public class Bird {
    private String diet;
    private String name;
    private String status;
    private String color;

    public String getName() {
        return name;
    }
    public String getDiet() {
        return diet;
    }
    public String getStatus() {
        return status;
    }
    public String getColor() {
        return color;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDiet(String diet) {
        this.diet = diet;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String toString() {
        return "{\"name\":\"" + name + "\",\"diet\":\"" + diet + "\",\"status\":\"" + status + "\",\"color\":\"" + color + "\"}"; 
    }
    public static void main(String[] args) {
        Bird bird1 = new Bird();
        bird1.setName("Sparrow");
        bird1.setDiet("Omnivore");
        bird1.setStatus("Least Concern");
        bird1.setColor("Brown");
        System.out.println(bird1);
    }
}
