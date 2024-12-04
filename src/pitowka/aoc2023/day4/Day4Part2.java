package pitowka.aoc2023.day4;

import pitowka.aoc.InputFileLines;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day4Part2 {
    public static void main(String[] args) {
        System.err.println(
            new AllCards(
                Stream.of(
                        "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
                        "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
                        "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
                        "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
                        "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
                        "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"
                    ).map(Card::new)
                    .toList())
                .totalCount()
        );

        System.err.println(
            new AllCards(
                new InputFileLines("/aoc2023/day4.txt")
                    .lines()
                    .map(Card::new)
                    .toList()
            ).totalCount()
        );
    }

    // Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53

    record AllCards(List<Card> cards) {
        public Integer totalCount() {
            return counts().values()
                .stream()
                .mapToInt(Integer::valueOf)
                .sum();
        }

        private Map<Integer, Integer> counts() {
            Map<Integer, Integer> counts = new HashMap<>();
            cards.forEach(c -> {
                counts.put(c.id, counts.getOrDefault(c.id, 0) + 1);

                IntStream.rangeClosed(c.id + 1, c.id + c.count())
                    .forEach(i -> counts.put(i, counts.getOrDefault(i, 0) + counts.get(c.id)));

            });

            return counts;
        }
    }

    record Card(Integer id, Collection<Integer> winning, Collection<Integer> all) {
        public static final Pattern CARD_PATTERN = Pattern.compile("Card\\s+(\\d+):\\s*(\\d.*\\d)\\s*\\|\\s*(\\d.*\\d)\\s*");

        public Card(String card) {
            this(CARD_PATTERN.matcher(card)
                .results()
                .findFirst()
                .orElseThrow()
            );
        }

        private Card(MatchResult mr) {
            this(
                Integer.valueOf(mr.group(1)),
                Arrays.stream(mr.group(2).split("\\s+")).map(Integer::valueOf).toList(),
                Arrays.stream(mr.group(3).split("\\s+")).map(Integer::valueOf).collect(Collectors.toSet()));
        }

        public Integer count() {
            return Integer.valueOf(
                String.valueOf(
                    winning.stream()
                        .filter(all::contains)
                        .count()));
        }
    }
}
