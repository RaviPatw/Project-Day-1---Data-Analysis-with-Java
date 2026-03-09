import java.io.*;
import java.util.*;

public class FOAP {
    private static File myFile;
    private static Scanner fileReader;



    public static void createFile(String filename) {
        myFile = new File(filename);
        try {
            fileReader = new Scanner(myFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found. Please enter a valid file.");
        }
    }



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

            if (fileReader != null) {
                fileReader.close();
            }
        }

        return linesList;
    }



    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);


        System.out.print("Enter the filename to read: ");
        String filename = inputScanner.nextLine();


        ArrayList<String> fileContents = getStringList(filename);
        System.out.println("\nContents of the file:");
        for (String line : fileContents) {
            System.out.println(line);
        }

        inputScanner.close();
    }
}

