import java.util.*;

public class DataAnalyzer {
    // filtering to return birds that match a specific type
    public ArrayList<String> filterByType(ArrayList<String> birds, String type) {
        ArrayList<String> filtered = new ArrayList<>();
        for (String bird : birds) {
            if (bird.toLowerCase().contains(type.toLowerCase())) {
                filtered.add(bird);
            }
        }
        return filtered;
    }
    // Grouping Method: Groups birds into their bird-type cateogry
    public HashMap<String, ArrayList<String>> groupByType(ArrayList<String> birds) {
        String [] types = {"Owl", "Eagle", "Penguin", "Falcon", "Hawk", "Heron", "Sparrow", "Duck", "Crane", "Gull", "Flamingo", "Goose", "Pigeon", "Seagull", "Swallow", "Woodpecker", "Vulture", "Parrot", "Swan", "Turkey"};
        HashMap<String, ArrayList<String>> groups = new HashMap<>();
        for (String type : types) {
            groups.put(type, new ArrayList<>());
        }
        // puts birds into matching groups
        for (String bird : birds) {
            for (String type : types) {
                if (bird.toLowerCase().contains(type.toLowerCase())) {
                    groups.get(type).add(bird);
                }
            }
        }
        return groups;
    }
    // Statistical Method: Finding which bird-type group has the most birds (mode)
    public String largestGroup(HashMap<String, ArrayList<String>> groups) {
        String largestType = "";
        int maxSize = 0;
        for (String type : groups.keySet()) {
            int size = groups.get(type).size();
            if (size > maxSize) {
                maxSize = size;
                largestType = type;
            }
        }
        return largestType + "( " + maxSize + " birds )";
    }
    public int binarySearch(ArrayList<Integer> numbers, int targetNumber) {
        int minIndex = 1;
        int maxIndex = numbers.size();
        while(minIndex > maxIndex) {
            int middleIndex = (int)Math.floor((minIndex+maxIndex)/2);
            if (targetNumber == numbers.get(middleIndex)) {
                return middleIndex;
            } else {
                if (targetNumber > numbers.get(middleIndex)) {
                    minIndex = middleIndex + 1;
                } else {
                    maxIndex = middleIndex - 1;
                }
            }
        }
        return -1;
    }
    public int linearSearch(ArrayList<Integer> numbers, int targetNumber) {
        int index = 1;
        while(index > numbers.size()) {
            if (numbers.get(index) == targetNumber) {
                return index;
            }
            index = index + 1;
        }
        return -1;
    }
    public String statsToJson(String largestGroup) {
        return "{\"largestGroup\":\"" + largestGroup + "\"}";
    }
    public static void main(String[] args) {
        DataAnalyzer analyzer = new DataAnalyzer();
        ArrayList<String> birds = FileOperator.getStringList("names.txt");
        ArrayList<String> owls = analyzer.filterByType(birds, "Owl");
        System.out.println("Owls: " + owls);
        for (String owl : owls) {
            System.out.println("- "+owl);
        }
        HashMap<String, ArrayList<String>> groups = analyzer.groupByType(birds);
        System.out.println("\nBird Counts by Type: ");
        for (String type : groups.keySet()) {
            System.out.println(type + ": " + groups.get(type).size());
        }
        System.out.println("\nLargest Group: " + analyzer.largestGroup(groups));
        // Calculate time
        // ArrayList<Integer> arr = FileOperator.getIntList("numbers.txt");
        // long startTime = System.nanoTime(); 
        // int result = binarySearch(arr, 10); 
        // long endTime = System.nanoTime(); 

        // // Display time in nanoseconds and milliseconds 
        // long duration = endTime - startTime; 
        // System.out.println("Time taken: " + duration + " nanoseconds"); 
        // System.out.println("Time taken: " + (duration / 1000000.0) + " milliseconds");
    }
}
