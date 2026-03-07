public class BirdAnalyzer {
  // Four 1D arrays to store bird data
  private String[] names;
  private String[] status;
  private String[] colors;
  private String[] diets;
  
  // No-argument constructor
  public BirdAnalyzer() {
    names = new String[0];
    status = new String[0];
    colors = new String[0];
    diets = new String[0];
  }
  
  // Parameterized constructor
  public BirdAnalyzer(String[] names, String[] status, String[] colors, String[] diets) {
    this.names = names;
    this.status = status;
    this.colors = colors;
    this.diets = diets;
  }
  
  public void diagnosticAnalysis() {
    if (names.length == 0) {
      System.out.println("No data available for analysis.");
      return;
    }
    
    System.out.println("Total birds in dataset: " + names.length);
    
    // Analyze conservation status
    int endangeredCount = 0;
    int vulnerableCount = 0;
    for (String s : status) {
      if (s.equals("Endangered")) endangeredCount++;
      if (s.equals("Vulnerable")) vulnerableCount++;
    }
    System.out.println("Endangered species: " + endangeredCount);
    System.out.println("Vulnerable species: " + vulnerableCount);
    
    // Analyze diets
    int carnivoreCount = 0;
    int herbivoreCount = 0;
    int omnivoreCount = 0;
    for (String d : diets) {
      if (d.contains("Carnivore")) carnivoreCount++;
      else if (d.contains("Herbivore")) herbivoreCount++;
      else if (d.contains("Omnivore")) omnivoreCount++;
    }
    System.out.println("Carnivores: " + carnivoreCount);
    System.out.println("Herbivores: " + herbivoreCount);
    System.out.println("Omnivores: " + omnivoreCount);
    
    // Find most common color
    String[] colorTypes = {"Red", "Blue", "Green", "Brown", "Black", "White", "Gray"};
    int[] colorCounts = new int[colorTypes.length];
    
    for (String color : colors) {
      for (int i = 0; i < colorTypes.length; i++) {
        if (color.contains(colorTypes[i])) {
          colorCounts[i]++;
          break;
        }
      }
    }
    
    int maxIndex = 0;
    for (int i = 1; i < colorCounts.length; i++) {
      if (colorCounts[i] > colorCounts[maxIndex]) {
        maxIndex = i;
      }
    }
    System.out.println("Most common color: " + colorTypes[maxIndex] + 
                      " (" + colorCounts[maxIndex] + " birds)");
  }
  
  public void groupByDiet() {
    System.out.println("\n=== Birds Grouped by Diet ===");
    
    String[] dietTypes = {"Carnivore", "Herbivore", "Omnivore"};
    
    for (String dietType : dietTypes) {
      System.out.println("\n" + dietType + ":");
      boolean found = false;
      for (int i = 0; i < names.length; i++) {
        if (diets[i].contains(dietType)) {
          System.out.println("  • " + names[i]);
          found = true;
        }
      }
      if (!found) System.out.println("  (None)");
    }
  }
  
  public void groupByStatus() {
    System.out.println("\n=== Birds Grouped by Conservation Status ===");
    
    String[] statusTypes = {"Least Concern", "Near Threatened", "Vulnerable", "Endangered"};
    
    for (String statusType : statusTypes) {
      System.out.println("\n" + statusType + ":");
      boolean found = false;
      for (int i = 0; i < names.length; i++) {
        if (status[i].equals(statusType)) {
          System.out.println("  • " + names[i]);
          found = true;
        }
      }
      if (!found) System.out.println("  (None)");
    }
  }
  
  public void groupByColor() {
    System.out.println("\n=== Birds Grouped by Primary Color ===");
    
    String[] colorTypes = {"Red", "Blue", "Green", "Brown", "Black", "White", "Gray"};
    
    for (String colorType : colorTypes) {
      System.out.println("\n" + colorType + ":");
      boolean found = false;
      for (int i = 0; i < names.length; i++) {
        if (colors[i].contains(colorType)) {
          System.out.println("  • " + names[i]);
          found = true;
        }
      }
      if (!found) System.out.println("  (None)");
    }
  }
  
  
  @Override
  public String toString() {
    return "Bird Dataset Analysis\n" +
           "======================\n" +
           "Dataset contains: " + names.length + " birds\n" +
           "Data categories: Names, Status, Colors, Diets\n" +
           "Use diagnosticAnalysis() for detailed statistics\n" +
           "======================";
  }
}