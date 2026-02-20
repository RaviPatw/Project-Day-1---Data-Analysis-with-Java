import java.util.*;
import java.io.*;

public class DataAnalyzer {
    // filtering to return birds that match a specific type
    public static ArrayList<String> filterByType(ArrayList<String> birds, String type) {
        ArrayList<String> filtered = new ArrayList<>();
        for (String bird : birds) {
            if (bird.toLowerCase().contains(type.toLowerCase())) {
                filtered.add(bird);
            }
        }
        return filtered;
    }
    // Grouping Method: Groups birds into their bird-type cateogry
    public static HashMap<String, ArrayList<String>> groupByType(ArrayList<String> birds) {
        String [] types = {"Owl", "Eagle", "Penguin", "Falcon", "Hawk", "Heron", "Sparrow", "Duck", "Crane", "Gull"};
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
    public static String largestGroup(HashMap<String, ArrayList<String>> groups) {
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
    public static int binarySearch(ArrayList<Integer> numbers, int targetNumber) {
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
    public static int linearSearch(ArrayList<Integer> numbers, int targetNumber) {
        int index = 1;
        while(index > numbers.size()) {
            if (numbers.get(index) == targetNumber) {
                return index;
            }
            index = index + 1;
        }
        return -1;
    }
    public static void main(String[] args) {
        // ArrayList<String> birds = FileOperator.getStringList("names.txt");
        // ArrayList<String> owls = filterByType(birds, "Owl");
        // System.out.println("Owls: " + owls);
        // for (String owl : owls) {
        //     System.out.println("- "+owl);
        // }
        // HashMap<String, ArrayList<String>> groups = groupByType(birds);
        // System.out.println("\nBird Counts by Type: ");
        // for (String type : groups.keySet()) {
        //     System.out.println(type + ": " + groups.get(type).size());
        // }
        // System.out.println("\nLargest Group: " + largestGroup(groups));
        // Calculate time
        ArrayList<Integer> arr = FileOperator.getIntList("numbers.txt");
        long startTime = System.nanoTime(); 
        int result = binarySearch(arr, 10); 
        long endTime = System.nanoTime(); 

        // Display time in nanoseconds and milliseconds 
        long duration = endTime - startTime; 
        System.out.println("Time taken: " + duration + " nanoseconds"); 
        System.out.println("Time taken: " + (duration / 1000000.0) + " milliseconds");
    }
}
