import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DataAnalyzer {
   public void Predictive_Analysis(){
        FileOperator fo = new FileOperator();
        System.out.println("Printing CSV using FileOperator:");
        fo.readFile("data.csv");
        ArrayList<Double> studyHours = new ArrayList<>();
        ArrayList<Double> gpas = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File("data.csv"));
            if (sc.hasNextLine()) sc.nextLine(); // skip header

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(",");
                studyHours.add(Double.parseDouble(parts[0]));
                gpas.add(Double.parseDouble(parts[1]));
            }

            sc.close();
        } catch (Exception e) {
            System.out.println("Error reading file for prediction: " + e.getMessage());
            return;
        }

        int n = studyHours.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;

        for (int i = 0; i < n; i++) {
            double x = studyHours.get(i);
            double y = gpas.get(i);
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
        }

        double slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
        double intercept = (sumY - slope * sumX) / n;

        System.out.printf("\nLinear Regression Formula: GPA = %.2f * StudyHours + %.2f\n", slope, intercept);

        Scanner input = new Scanner(System.in);
        System.out.print("Enter hours you plan to study: ");
        double hours = input.nextDouble();
        input.close();

        double predictedGPA = slope * hours + intercept;
        System.out.printf("Predicted GPA for %.1f study hours: %.2f\n", hours, predictedGPA);
    }
   public static void main(String[] args) {
    DataAnalyzer PredictiveAnalyzer = new DataAnalyzer();
    PredictiveAnalyzer.Predictive_Analysis();
   }
}