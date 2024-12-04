package pitowka.aoc2023.day4;

import pitowka.aoc.InputFileLines;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4Part1 {
    public static void main(String[] args) {
        System.err.println(
            new InputFileLines("/aoc2023/day4.txt")
                .lines()
                .map(Card::new)
                .mapToInt(Card::value)
                .sum()
        );
    }

    record Card(Integer id, Collection<Integer> winning, Collection<Integer> all){
        public static final Pattern CARD_PATTERN = Pattern.compile("Card\\s+(\\d+):\\s*(\\d.*\\d)\\s*\\|\\s*(\\d.*\\d)\\s*");

        public Card(String card){
            this(CARD_PATTERN.matcher(card)
                .results()
                .findFirst()
                .orElseThrow()
            );
        }

        private Card(MatchResult mr){
            this(
                Integer.valueOf(mr.group(1)),
                Arrays.stream(mr.group(2).split("\\s+")).map(Integer::valueOf).toList(),
                Arrays.stream(mr.group(3).split("\\s+")).map(Integer::valueOf).collect(Collectors.toSet()));
        }

        public Integer value(){
            long count = winning.stream()
                .filter(all::contains)
                .count();

            return count > 0 ? 1 << count - 1 : 0;
        }
    }
}
