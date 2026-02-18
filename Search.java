import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Search {
    public static int binarySearch(ArrayList<Integer> arr, int target)  throws FileNotFoundException {
        int low = 0;
        int high = arr.size() - 1;
        while (low <= high) {
            int mid = (int)Math.floor((low + high)/ 2);
            if (arr.get(mid) == target) {
                return mid;
            } 
            else if (arr.get(mid) < target) {
                low = mid + 1;
            } 
            else {
                high = mid - 1;
            }
        }
        return -1;
    }
    public static int linearSearch(ArrayList<Integer> arr, int target) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) == target) {
                return i;
            }
        }
        return -1;
    }


    public static void main(String[] args) throws FileNotFoundException  {

        FileOperator fo = new FileOperator();
        fo.readFile("numbers.txt");
        ArrayList<Integer> numbersList = new ArrayList<>();
        Scanner scanner = new Scanner(new File("numbers.txt"));
        while (scanner.hasNextInt()) {
            numbersList.add(scanner.nextInt());
        }
        scanner.close();
        int target = 7;
        long startTime = System.nanoTime();
        int result = binarySearch(numbersList, target);
        int linear = linearSearch(numbersList, target);
        long endTime = System.nanoTime();
        if (result != -1) {
            System.out.println("Element found at index: " + result);
        } else {
            System.out.println("Element not found.");
        }
        if (linear != -1) {
            System.out.println("Element found at index: " + result);
        } else {
            System.out.println("Element not found.");
        }
        long duration = endTime - startTime;
        System.out.println("Time taken: " + duration + " nanoseconds");
        System.out.println("Time taken: " + (duration / 1000000.0) + " milliseconds");
    }
}