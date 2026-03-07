import java.io.*;
import java.util.*;

public class FOAP {
    private static File myFile;
    private static Scanner fileReader;

    /*
     * Creates the File and Scanner to read the specified filename
     */
    public static void createFile(String filename) {
        myFile = new File(filename);
        try {
            fileReader = new Scanner(myFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found. Please enter a valid file.");
        }
    }

    /*
     * Returns an ArrayList of Strings from a file
     */
    public static ArrayList<String> getStringList(String filename) {
        createFile(filename);
        ArrayList<String> linesList = new ArrayList<>();

        try {
            while (fileReader.hasNextLine()) {
                linesList.add(fileReader.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred while reading the file.");
        } finally {
            // Ensure the Scanner is closed to release resources
            if (fileReader != null) {
                fileReader.close();
            }
        }

        return linesList;
    }

    /*
     * Main method to test the FileOperator class
     */
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);

        // Prompt the user to enter a filename
        System.out.print("Enter the filename to read: ");
        String filename = inputScanner.nextLine();

        // Read the file and print its contents
        ArrayList<String> fileContents = getStringList(filename);
        System.out.println("\nContents of the file:");
        for (String line : fileContents) {
            System.out.println(line);
        }

        inputScanner.close();
    }
}

