package pitowka.aoc2023.day1;

import pitowka.aoc.InputFileLines;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Day1 {

    public static void main(String[] args){
        System.err.println(
            new InputFileLines("/aoc2023/day1.txt")
                .lines()
                .map(NumberFromString::new)
                .mapToInt(NumberFromString::number)
                .sum()
        );

        System.err.println(
            new InputFileLines("/aoc2023/day1.txt")
                .lines()
                .map(NumberFromText::new)
                .mapToInt(NumberFromText::number)
                .sum()
        );
    }

    public static class NumberFromString {
        private static final Pattern FIRST_NUMBER = Pattern.compile("^\\D*(\\d).*");
        private static final Pattern LAST_NUMBER = Pattern.compile("^.*(\\d)\\D*$");
        private final String s;

        public NumberFromString(String s) {
            this.s = s;
        }

        public Integer number(){
            return Integer.valueOf(
                "%s%s".formatted(number(FIRST_NUMBER), number(LAST_NUMBER))
            );
        }

        private String number(Pattern p){
            return p
                .matcher(s)
                .results()
                .map(m -> m.group(1))
                .findFirst()
                .orElse(null);
        }
    }

    public static class NumberFromText {
        private static final String NUMBERS = "\\d|one|two|three|four|five|six|seven|eight|nine";
        private static final Pattern FIRST_NUMBER = Pattern.compile("^.*?("+NUMBERS+").*");
        private static final Pattern LAST_NUMBER = Pattern.compile(".*("+NUMBERS+").*?$");

        private static final Map<String, Integer> MAPPING = new HashMap<>(Map.of(
                "one", 1,
                "two", 2,
                "three", 3,
                "four", 4,
                "five", 5,
                "six", 6,
                "seven", 7,
                "eight", 8,
                "nine", 9

        ));
        private final String s;

        public NumberFromText(String s) {
            this.s = s;
        }

        public Integer number(){
            return Integer.valueOf(
                "%s%s".formatted(
                    MAPPING.computeIfAbsent(number(FIRST_NUMBER), Integer::valueOf),
                    MAPPING.computeIfAbsent(number(LAST_NUMBER), Integer::valueOf)
                )
            );
        }

        private String number(Pattern p){
            return p
                    .matcher(s)
                    .results()
                    .map(m -> m.group(1))
                    .findFirst()
                    .orElse(null);
        }
    }
}
