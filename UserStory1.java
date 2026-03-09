import java.util.ArrayList;
import java.util.HashMap;

public class UserStory1 {
    public static void main(String[] args) {
        System.out.println("User Story: As an analyst, I want to group birds by diet and status");
        System.out.println("so that I can identify patterns in conservation data.");
        System.out.println();

        DataAnalyzer analyzer = new DataAnalyzer();
        ArrayList<String> birds = FileOperator.getStringList("names.txt");
        HashMap<String, ArrayList<String>> groups = analyzer.groupByType(birds);
        System.out.println("Largest group: " + analyzer.largestGroup(groups));
    }
}
