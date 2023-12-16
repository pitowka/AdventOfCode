package pitowka.aoc2023.day7;

import java.util.Arrays;

public enum Card {
    A("A", "E"),
    K("K", "D"),
    Q("Q", "C"),
    J("J", "B"),
    T("T", "A"),
    _9("9", "9"),
    _8("8", "8"),
    _7("7", "7"),
    _6("6", "6"),
    _5("5", "5"),
    _4("4", "4"),
    _3("3", "3"),
    _2("2", "2");

    private final String label;
    private final String hex;


    private Card(String label, String hex) {
        this.label = label;
        this.hex = hex;
    }

    public Integer hexValue(){
        return Integer.parseInt(hex, 16);
    }

    public String hex(){
        return hex;
    }

    public static Card ofCharacter(Character name) {
        return ofString(String.valueOf(name));
    }

    public static Card ofString(String label) {
        return Arrays.stream(values())
            .filter(c -> c.label.equals(label))
            .findFirst()
            .orElseThrow();
    }
}
