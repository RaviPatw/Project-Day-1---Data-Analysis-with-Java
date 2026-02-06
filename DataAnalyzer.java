import java.io.*;
import java.util.*;

public class DataAnalyzer {
    private Map<String, Integer> categoryCounts;
    private Map<String, Integer> dietCounts;
    
    public DataAnalyzer() {
        this.categoryCounts = new HashMap<>();
        this.dietCounts = new HashMap<>();
    }
    
    public void countCategoriesFromFile(String filename) throws IOException {
        // Reset counts
        categoryCounts.clear();
        
        // Read file and count categories
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        
        while ((line = reader.readLine()) != null) {
            String category = line.trim();
            if (!category.isEmpty()) {
                categoryCounts.put(category, 
                    categoryCounts.getOrDefault(category, 0) + 1);
            }
        }
        
        reader.close();
    }
    
    public void countDietsFromFile(String filename) throws IOException {
        // Reset counts
        dietCounts.clear();
        
        // Read file and count diets
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;
        
        while ((line = reader.readLine()) != null) {
            String diet = line.trim();
            if (!diet.isEmpty()) {
                dietCounts.put(diet, 
                    dietCounts.getOrDefault(diet, 0) + 1);
            }
        }
        
        reader.close();
    }
    
    public void countCategories(String[] categories) {
        // Reset counts
        categoryCounts.clear();
        
        // Count each category
        for (String category : categories) {
            String trimmedCategory = category.trim();
            categoryCounts.put(trimmedCategory, 
                categoryCounts.getOrDefault(trimmedCategory, 0) + 1);
        }
    }
    
    public void countDiets(String[] diets) {
        // Reset counts
        dietCounts.clear();
        
        // Count each diet
        for (String diet : diets) {
            String trimmedDiet = diet.trim();
            dietCounts.put(trimmedDiet, 
                dietCounts.getOrDefault(trimmedDiet, 0) + 1);
        }
    }
    
    public void printConservationResults() {
        System.out.println("Conservation Status Counts:");
        System.out.println("-".repeat(30));
        
        // Print in order of severity
        String[] orderedCategories = {
            "Critically Endangered",
            "Endangered",
            "Vulnerable",
            "Near Threatened",
            "Least Concern"
        };
        
        for (String category : orderedCategories) {
            int count = categoryCounts.getOrDefault(category, 0);
            System.out.println(category + ": " + count);
        }
    }
    
    public void printDietResults() {
        System.out.println("Diet Type Counts:");
        System.out.println("-".repeat(30));
        
        // Print all diet types found
        dietCounts.forEach((diet, count) -> 
            System.out.println(diet + ": " + count)
        );
    }
    
    public int getConservationCount(String category) {
        return categoryCounts.getOrDefault(category, 0);
    }
    
    public int getDietCount(String diet) {
        return dietCounts.getOrDefault(diet, 0);
    }
    
    public Map<String, Integer> getAllConservationCounts() {
        return new HashMap<>(categoryCounts);
    }
    
    public Map<String, Integer> getAllDietCounts() {
        return new HashMap<>(dietCounts);
    }
    
    public int getTotalConservationCount() {
        return categoryCounts.values().stream()
            .mapToInt(Integer::intValue)
            .sum();
    }
    
    public int getTotalDietCount() {
        return dietCounts.values().stream()
            .mapToInt(Integer::intValue)
            .sum();
    }
    
    public static void main(String[] args) {
        DataAnalyzer analyzer = new DataAnalyzer();
        
        try {
            // Count conservation statuses
            if (args.length > 0) {
                analyzer.countCategoriesFromFile(args[0]);
            } else {
                analyzer.countCategoriesFromFile("status.txt");
            }
            
            analyzer.printConservationResults();
            System.out.println("\nTotal species: " + analyzer.getTotalConservationCount());
            
            // Count diets
            System.out.println("\n");
            if (args.length > 1) {
                analyzer.countDietsFromFile(args[1]);
            } else {
                analyzer.countDietsFromFile("diets.txt");
            }
            
            analyzer.printDietResults();
            System.out.println("\nTotal species: " + analyzer.getTotalDietCount());
            
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            System.out.println("Make sure the file exists in the correct location.");
        }
    }
}