import java.util.*;

public class DataAnalyzer {
    private ConservationBusiness business;
    private ArrayList<String> birds;

    public DataAnalyzer() {}

    public DataAnalyzer(ConservationBusiness business) {
        this.business = business;
    }

    public void loadData() {
        birds = FileOperator.getStringList("names.txt");
        System.out.println("Loaded " + birds.size() + " birds.");
        if (business != null) {
            System.out.println(business.getSummary());
        }
    }

    public void runPrediction(Scanner input) {
        if (birds == null || birds.isEmpty()) {
            System.out.println("No data loaded. Call loadData() first.");
            return;
        }
        System.out.print("Enter a bird type to analyze (e.g. Owl, Eagle): ");
        String type = input.nextLine().trim();
        ArrayList<String> filtered = filterByType(birds, type);
        System.out.println("Found " + filtered.size() + " match(es):");
        for (String bird : filtered) {
            System.out.println("  - " + bird);
        }
        HashMap<String, ArrayList<String>> groups = groupByType(birds);
        System.out.println("Most common type: " + largestGroup(groups));
    }

    public ArrayList<String> filterByType(ArrayList<String> birds, String type) {
        ArrayList<String> filtered = new ArrayList<>();
        for (String bird : birds) {
            if (bird.toLowerCase().contains(type.toLowerCase())) {
                filtered.add(bird);
            }
        }
        return filtered;
    }
    public HashMap<String, ArrayList<String>> groupByType(ArrayList<String> birds) {
        String [] types = {"Owl", "Eagle", "Penguin", "Falcon", "Hawk", "Heron", "Sparrow", "Duck", "Crane", "Gull", "Flamingo", "Goose", "Pigeon", "Seagull", "Swallow", "Woodpecker", "Vulture", "Parrot", "Swan", "Turkey"};
        HashMap<String, ArrayList<String>> groups = new HashMap<>();
        for (String type : types) {
            groups.put(type, new ArrayList<>());
        }
        for (String bird : birds) {
            for (String type : types) {
                if (bird.toLowerCase().contains(type.toLowerCase())) {
                    groups.get(type).add(bird);
                }
            }
        }
        return groups;
    }
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










    }
}
