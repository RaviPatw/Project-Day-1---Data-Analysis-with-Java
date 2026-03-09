public class Read1 {
    public static void main(String[] args) {
        String[] files = {"diets.txt", "colors.txt", "names.txt", "status.txt"};
        FileOperator1 fileOp = new FileOperator1();

        for (String filename : files) {
            System.out.println("=== " + filename.toUpperCase() + " ===");
            String[] lines = FileOperator1.toStringArray(filename);
            for (int i = 0; i < lines.length; i++) {
                System.out.println((i + 1) + ". " + lines[i]);
            }
            System.out.println("Total entries: " + lines.length);
            System.out.println();
        }
    }
}
