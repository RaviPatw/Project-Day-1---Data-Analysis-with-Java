import java.util.Scanner;

public class DataRunner {
    public static void main(String[] args) {
        BirdStudy student = new BirdStudy();
        System.out.println(student.toString());
        student.analyzeBirds();
        
        student.groupSimilarBirds();
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter a diet type to search for (Carnivore/Herbivore/Omnivore): ");
        String searchDiet = scanner.nextLine();
        student.findBirdsByDiet(searchDiet);
        
        scanner.close();
    }
}

class BirdStudy {
    private String[] birdNames;
    private String[] birdDiets;
    private String[] conservationStatus;
    
    public BirdStudy() {
        birdNames = FileReader.toStringArray("names.txt");
        birdDiets = FileReader.toStringArray("diets.txt");
        conservationStatus = FileReader.toStringArray("status.txt");
        
        if (birdNames == null || birdNames.length == 0) {
            initializeSampleData();
        }
    }
    
    public BirdStudy(String[] names, String[] diets, String[] status) {
        this.birdNames = names;
        this.birdDiets = diets;
        this.conservationStatus = status;
    }
    
    public void analyzeBirds() {
        System.out.println("\n=== DIAGNOSTIC ANALYSIS ===");
        System.out.println("Total birds: " + birdNames.length);
        
        int carnivores = 0;
        int herbivores = 0;
        int omnivores = 0;
        
        for (String diet : birdDiets) {
            if (diet.toLowerCase().contains("carnivore")) {
                carnivores++;
            } else if (diet.toLowerCase().contains("herbivore")) {
                herbivores++;
            } else if (diet.toLowerCase().contains("omnivore")) {
                omnivores++;
            }
        }
        
        System.out.println("Carnivores: " + carnivores);
        System.out.println("Herbivores: " + herbivores);
        System.out.println("Omnivores: " + omnivores);
        
        int endangered = 0;
        int vulnerable = 0;
        int leastConcern = 0;
        
        for (String status : conservationStatus) {
            if (status.equals("Endangered")) {
                endangered++;
            } else if (status.equals("Vulnerable")) {
                vulnerable++;
            } else if (status.equals("Least Concern")) {
                leastConcern++;
            }
        }
        
        System.out.println("Endangered: " + endangered);
        System.out.println("Vulnerable: " + vulnerable);
        System.out.println("Least Concern: " + leastConcern);
    }
    
    public void groupSimilarBirds() {
        System.out.println("\n=== STUDY GROUPS (Similar Birds) ===");
        
        // Group 1: Birds by diet
        System.out.println("\nGroup 1 - Diet-Based Groups:");
        System.out.println("Carnivores:");
        for (int i = 0; i < birdNames.length; i++) {
            if (birdDiets[i].toLowerCase().contains("carnivore")) {
                System.out.println("  - " + birdNames[i]);
            }
        }
        
        System.out.println("\nHerbivores:");
        for (int i = 0; i < birdNames.length; i++) {
            if (birdDiets[i].toLowerCase().contains("herbivore")) {
                System.out.println("  - " + birdNames[i]);
            }
        }
        
        System.out.println("\nOmnivores:");
        for (int i = 0; i < birdNames.length; i++) {
            if (birdDiets[i].toLowerCase().contains("omnivore")) {
                System.out.println("  - " + birdNames[i]);
            }
        }
        
        // Group 2: Birds by conservation status
        System.out.println("\nGroup 2 - Conservation Status Groups:");
        System.out.println("Endangered (Priority Study):");
        for (int i = 0; i < birdNames.length; i++) {
            if (conservationStatus[i].equals("Endangered")) {
                System.out.println("  - " + birdNames[i]);
            }
        }
        
        System.out.println("\nVulnerable:");
        for (int i = 0; i < birdNames.length; i++) {
            if (conservationStatus[i].equals("Vulnerable")) {
                System.out.println("  - " + birdNames[i]);
            }
        }
    }
    
    /**
     * Method to find birds by specific diet - manipulates array elements
     * Precondition: Arrays are initialized
     * Postcondition: Returns birds matching the diet
     */
    public void findBirdsByDiet(String dietType) {
        System.out.println("\n=== BIRDS WITH " + dietType.toUpperCase() + " DIET ===");
        boolean found = false;
        
        for (int i = 0; i < birdNames.length; i++) {
            if (birdDiets[i].toLowerCase().contains(dietType.toLowerCase())) {
                System.out.println(birdNames[i] + " - Status: " + conservationStatus[i]);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No birds found with " + dietType + " diet.");
        }
    }
    
    /**
     * toString method providing general information about the dataset
     * Precondition: Arrays are initialized
     * Postcondition: Returns string with dataset summary
     */
    @Override
    public String toString() {
        return "AVIAN VETERINARY STUDY DATASET\n" +
               "===============================\n" +
               "Student: Avian Veterinary Student\n" +
               "Purpose: Identify groups of similar birds for final exam study\n" +
               "Dataset Size: " + birdNames.length + " birds\n" +
               "Data Categories: Names, Diets, Conservation Status\n" +
               "File Sources: names.txt, diets.txt, status.txt\n" +
               "===============================";
    }
    
    /**
     * Initialize sample data if files are empty
     * Precondition: Arrays are empty or null
     * Postcondition: Arrays contain sample data
     */
    private void initializeSampleData() {
        System.out.println("Initializing with sample bird data...");
        
        // Sample bird data
        birdNames = new String[] {
            "Bald Eagle", "American Robin", "Emperor Penguin", 
            "Great Horned Owl", "Scarlet Macaw", "Peregrine Falcon",
            "Canada Goose", "Atlantic Puffin", "Ruby-throated Hummingbird"
        };
        
        birdDiets = new String[] {
            "Carnivore", "Omnivore", "Carnivore",
            "Carnivore", "Herbivore", "Carnivore",
            "Herbivore", "Carnivore", "Omnivore"
        };
        
        conservationStatus = new String[] {
            "Least Concern", "Least Concern", "Near Threatened",
            "Least Concern", "Endangered", "Least Concern",
            "Least Concern", "Vulnerable", "Least Concern"
        };
    }
}

// Note: FileReader class should be provided by your environment
// If not, you can use this simplified version:
class FileReader {
    public static String[] toStringArray(String filename) {
        // Simplified version - returns empty array
        // In actual assignment, this reads from file
        return new String[0];
    }
    
    public static int[] toIntArray(String filename) {
        return new int[0];
    }
    
    public static double[] toDoubleArray(String filename) {
        return new double[0];
    }
}