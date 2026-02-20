import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DataAnalyzer {

    public void Predictive_Analysis() {

        ArrayList<Double> years = new ArrayList<>();
        ArrayList<Double> populations = new ArrayList<>();

        try {
            Scanner sc = new Scanner(new File("bird_population.csv"));

            if (sc.hasNextLine()) sc.nextLine();

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(",");

                years.add(Double.parseDouble(parts[0]));
                populations.add(Double.parseDouble(parts[1]));
            }

            sc.close();

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        int n = years.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (int i = 0; i < n; i++) {
            double x = years.get(i);
            double y = populations.get(i);

            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
        }

        double slope = (n * sumXY - sumX * sumY) /
                       (n * sumX2 - sumX * sumX);

        double intercept = (sumY - slope * sumX) / n;

        System.out.printf("\nLinear Regression Formula:\n");
        System.out.printf("Population = %.2f * Year + %.2f\n", slope, intercept);

        Scanner input = new Scanner(System.in);
        System.out.print("\nEnter a future year to predict bird population: ");
        double futureYear = input.nextDouble();

        double predictedPopulation = slope * futureYear + intercept;

        System.out.printf("Predicted bird population for %.0f: %.0f birds\n",
                          futureYear, predictedPopulation);

        input.close();
    }

    public static void main(String[] args) {
        DataAnalyzer analyzer = new DataAnalyzer();
        analyzer.Predictive_Analysis();
    }
}