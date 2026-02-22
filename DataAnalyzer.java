import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataAnalyzer {
    public static final String DEFAULT_FALLBACK_STATUS = "Least Concern";
    public static int totalPredictions = 0;

    private ConservationBusiness business;
    private ArrayList<String> names;
    private ArrayList<String> statuses;
    private ArrayList<String> colors;
    private ArrayList<String> diets;

    public DataAnalyzer() {
        this.business = new ConservationBusiness();
        names = new ArrayList<>();
        statuses = new ArrayList<>();
        colors = new ArrayList<>();
        diets = new ArrayList<>();
    }

    public DataAnalyzer(ConservationBusiness business) {
        this.business = business;
        names = new ArrayList<>();
        statuses = new ArrayList<>();
        colors = new ArrayList<>();
        diets = new ArrayList<>();
    }

    public void loadData() {
        try {
            names = readLines("names.txt");
            statuses = readLines("status.txt");
            colors = readLines("colors.txt");
            diets = readLines("diets.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Could not read data file: " + e.getMessage());
        }
    }

    public void runPrediction(Scanner input) {
        if (names.size() == 0 || statuses.size() != names.size() || colors.size() != names.size() || diets.size() != names.size()) {
            System.out.println("Data is missing or file lengths do not match.");
            return;
        }

        System.out.println("\n" + business.getSummary());
        System.out.print("Enter bird color: ");
        String userColor = normalize(input.nextLine());
        System.out.print("Enter diet phrase (example: flying insects): ");
        String userDietPhrase = normalize(input.nextLine());

        String userDietKeyword = firstWord(userDietPhrase);

        String predictedStatus = predictStatus(userColor, userDietKeyword);
        totalPredictions++;

        System.out.println("\nPredicted conservation status: " + predictedStatus);
        System.out.println(generateSimpleReportLine(predictedStatus));
        System.out.println("Total predictions made: " + totalPredictions);
    }

    private String predictStatus(String userColor, String userDietKeyword) {
        ArrayList<String> matchStatuses = new ArrayList<>();
        ArrayList<Integer> matchCounts = new ArrayList<>();

        for (int i = 0; i < names.size(); i++) {
            String rowColor = normalize(colors.get(i));
            String rowDietKeyword = firstWord(normalize(diets.get(i)));

            if (rowColor.equals(userColor) && rowDietKeyword.equals(userDietKeyword)) {
                String rowStatus = statuses.get(i);
                int index = matchStatuses.indexOf(rowStatus);

                if (index == -1) {
                    matchStatuses.add(rowStatus);
                    matchCounts.add(1);
                } else {
                    matchCounts.set(index, matchCounts.get(index) + 1);
                }
            }
        }

        if (matchStatuses.size() == 0) {
            return getMostCommonOverallStatus();
        }

        int bestIndex = 0;
        for (int i = 1; i < matchCounts.size(); i++) {
            if (matchCounts.get(i) > matchCounts.get(bestIndex)) {
                bestIndex = i;
            }
        }

        return matchStatuses.get(bestIndex);
    }

    private String getMostCommonOverallStatus() {
        if (statuses.size() == 0) {
            return DEFAULT_FALLBACK_STATUS;
        }

        ArrayList<String> uniqueStatuses = new ArrayList<>();
        ArrayList<Integer> counts = new ArrayList<>();

        for (int i = 0; i < statuses.size(); i++) {
            String status = statuses.get(i);
            int index = uniqueStatuses.indexOf(status);

            if (index == -1) {
                uniqueStatuses.add(status);
                counts.add(1);
            } else {
                counts.set(index, counts.get(index) + 1);
            }
        }

        int bestIndex = 0;
        for (int i = 1; i < counts.size(); i++) {
            if (counts.get(i) > counts.get(bestIndex)) {
                bestIndex = i;
            }
        }

        return uniqueStatuses.get(bestIndex);
    }

    public static String normalize(String text) {
        if (text == null) {
            return "";
        }
        String cleaned = text.toLowerCase().replace(".", " ").replace(",", " ").trim();
        return cleaned;
    }

    public static String firstWord(String text) {
        if (text.length() == 0) {
            return "";
        }
        String[] words = text.split("\\s+");
        return words[0];
    }
    
    private String generateSimpleReportLine(String predictedStatus) {
        String statusLower = predictedStatus.toLowerCase();
        if (statusLower.contains("critical")) {
            return "Report: This bird may need urgent conservation support.";
        } else if (statusLower.contains("endangered") || statusLower.contains("vulnerable")) {
            return "Report: This bird may need active conservation planning.";
        }
        return "Report: This bird is usually in a stable category.";
    }

    // Part 2 (web): build chart data from existing bird files.
    // Each Bird object is: status name + count of birds in that status.
    public ArrayList<Bird> getBirdData() {
        if (names.size() == 0 || statuses.size() == 0 || colors.size() == 0 || diets.size() == 0) {
            loadData();
        }

        ArrayList<Bird> birdData = new ArrayList<>();
        if (names.size() == 0 || statuses.size() != names.size() || colors.size() != names.size() || diets.size() != names.size()) {
            return birdData;
        }

        ArrayList<String> statusNames = new ArrayList<>();
        ArrayList<Integer> statusCounts = new ArrayList<>();

        for (int i = 0; i < statuses.size(); i++) {
            String status = statuses.get(i);
            int index = statusNames.indexOf(status);
            if (index == -1) {
                statusNames.add(status);
                statusCounts.add(1);
            } else {
                statusCounts.set(index, statusCounts.get(index) + 1);
            }
        }

        for (int i = 0; i < statusNames.size(); i++) {
            birdData.add(new Bird(statusNames.get(i), statusCounts.get(i)));
        }

        return birdData;
    }

    public String birdsToJson(ArrayList<Bird> birds) {
        String json = "[";
        for (int i = 0; i < birds.size(); i++) {
            json += birds.get(i).toString();
            if (i < birds.size() - 1) {
                json += ",";
            }
        }
        json += "]";
        return json;
    }

    public Bird findMin(ArrayList<Bird> birds) {
        if (birds.size() == 0) {
            return new Bird("None", 0);
        }

        Bird minBird = birds.get(0);
        for (int i = 1; i < birds.size(); i++) {
            if (birds.get(i).getValue() < minBird.getValue()) {
                minBird = birds.get(i);
            }
        }
        return minBird;
    }

    public Bird findMax(ArrayList<Bird> birds) {
        if (birds.size() == 0) {
            return new Bird("None", 0);
        }

        Bird maxBird = birds.get(0);
        for (int i = 1; i < birds.size(); i++) {
            if (birds.get(i).getValue() > maxBird.getValue()) {
                maxBird = birds.get(i);
            }
        }
        return maxBird;
    }

    public double findAvg(ArrayList<Bird> birds) {
        if (birds.size() == 0) {
            return 0.0;
        }

        int sum = 0;
        for (int i = 0; i < birds.size(); i++) {
            sum += birds.get(i).getValue();
        }
        return sum * 1.0 / birds.size();
    }

    public String statsToJson(ArrayList<Bird> birds) {
        Bird min = findMin(birds);
        Bird max = findMax(birds);
        double avg = findAvg(birds);
        return "{\"count\":" + birds.size()
                + ",\"minName\":\"" + min.getName() + "\""
                + ",\"minValue\":" + min.getValue()
                + ",\"maxName\":\"" + max.getName() + "\""
                + ",\"maxValue\":" + max.getValue()
                + ",\"avg\":" + String.format("%.2f", avg)
                + "}";
    }

    private ArrayList<String> readLines(String fileName) throws FileNotFoundException {
        ArrayList<String> rows = new ArrayList<>();
        Scanner scanner = new Scanner(new File(fileName));
        while (scanner.hasNextLine()) {
            rows.add(scanner.nextLine().trim());
        }
        scanner.close();
        return rows;
    }
}
