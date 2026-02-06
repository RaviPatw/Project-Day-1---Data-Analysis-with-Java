import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileOperator {
    public void readFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        }
    }
    // NEW METHOD: Returns a String array from a file
    public static String[] toStringArray(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            // Return empty array if file doesn't exist
            return new String[0];
        }
        // Convert ArrayList to regular array
        return lines.toArray(new String[0]);
    }
    
    // NEW METHOD: Returns an int array from a file (optional)
    public static int[] toIntArray(String filename) {
        ArrayList<Integer> numbers = new ArrayList<>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    try {
                        numbers.add(Integer.parseInt(line));
                    } catch (NumberFormatException e) {
                        // Skip non-integer lines
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            return new int[0];
        }
        // Convert ArrayList<Integer> to int[]
        int[] result = new int[numbers.size()];
        for (int i = 0; i < numbers.size(); i++) {
            result[i] = numbers.get(i);
        }
        return result;
    }
    
    // NEW METHOD: Returns a double array from a file (optional)
    public static double[] toDoubleArray(String filename) {
        ArrayList<Double> numbers = new ArrayList<>();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    try {
                        numbers.add(Double.parseDouble(line));
                    } catch (NumberFormatException e) {
                        // Skip non-double lines
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
            return new double[0];
        }
        // Convert ArrayList<Double> to double[]
        double[] result = new double[numbers.size()];
        for (int i = 0; i < numbers.size(); i++) {
            result[i] = numbers.get(i);
        }
        return result;
    }

}