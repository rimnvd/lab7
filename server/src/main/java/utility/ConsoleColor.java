package utility;

public enum ConsoleColor {
    ANSI_RED("\u001B[31m"),
    ANSI_RESET("\u001B[0m");

    private final String color;

    ConsoleColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }


}
