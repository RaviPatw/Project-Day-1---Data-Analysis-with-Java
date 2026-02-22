public class Bird {
    private String name;
    private int value;

    public Bird(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        String safeName = name.replace("\\", "\\\\").replace("\"", "\\\"");
        return "{\"name\":\"" + safeName + "\",\"value\":" + value + "}";
    }
}
