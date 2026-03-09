import java.util.Scanner;

public class UserStory {
    public static void main(String[] args) {
        System.out.println("User Story: As a student, I want to read bird data from files");
        System.out.println("so that I can view names, diets, colors, and conservation status.");
        System.out.println();

        FileOperator fileOp = new FileOperator();
        fileOp.readFile("names.txt");
    }
}
