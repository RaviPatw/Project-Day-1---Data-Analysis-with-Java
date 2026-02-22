import java.util.Scanner;

public class DataRunner {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter business name (or press Enter for default): ");
        String businessName = input.nextLine().trim();
        System.out.print("Enter analyst name (or press Enter for default): ");
        String analystName = input.nextLine().trim();

        DataAnalyzer analyzer;

        if (businessName.length() == 0 || analystName.length() == 0) {
            analyzer = new DataAnalyzer();
        } else {
            ConservationBusiness customBusiness = new ConservationBusiness(businessName, analystName);
            analyzer = new DataAnalyzer(customBusiness);
        }

        analyzer.loadData();
        analyzer.runPrediction(input);
    }
}
