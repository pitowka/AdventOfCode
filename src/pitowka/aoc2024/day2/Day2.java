package pitowka.aoc2024.day2;

import pitowka.aoc.InputFileLines;
import pitowka.aoc2024.day1.Day1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day2 {
    public static void main(String[] args) {
        System.err.println(
            new InputFileLines("/aoc2024/day2.txt")
                .lines()
                .map(Report::new)
                    .filter(Report::safe)
                    .count()
            );

        System.err.println(
                new InputFileLines("/aoc2024/day2.txt")
                        .lines()
                        .map(Report::new)
                        .filter(Report::safeWithTolerance)
                        .count()
        );
    }

    record Report(List<Integer> levels){
        Report(String levels){
            this(Stream.of(levels.split("\\s+"))
                .map(Integer::valueOf)
                .toList());
        }

        boolean safe(){
            return IntStream.range(1, levels.size())
                .map(i -> Math.abs(levels.get(i) - levels.get(i - 1)))
                .mapToObj(d -> d >= 1 && d <= 3)
                .allMatch(Boolean::booleanValue)
            &&
                (levels.equals(levels.stream().sorted().toList())
                || levels.equals(levels.stream().sorted(Comparator.reverseOrder()).toList()));
        }

        boolean safeWithTolerance(){
            return Stream.concat(
                    Stream.of(this),
                    with1Removed())
                    .anyMatch(Report::safe);
        }

        private Stream<Report> with1Removed(){
            return IntStream.range(0, levels.size())
                    .mapToObj(i -> {
                        List<Integer> retVal = new ArrayList<>(levels);
                        retVal.remove(i);
                        return retVal;
                    })
                    .map(Report::new);
        }
    }
}
