public class Read {
    public static void main(String[] args) {
        FileOperator fileOp = new FileOperator();
        fileOp.readFile("diets.txt");
        fileOp.readFile("colors.txt");
        fileOp.readFile("names.txt");
        fileOp.readFile("status.txt");

    }
}