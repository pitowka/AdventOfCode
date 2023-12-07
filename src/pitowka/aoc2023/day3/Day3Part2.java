package pitowka.aoc2023.day3;

import pitowka.aoc.InputFileLines;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day3Part2 {

    public static void main(String[] args) {
        String test = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...$.*....
            .664.598..""";


        System.err.println(
            new GearMap(Arrays.asList(test.split("\n")))
                .realGears()
                .mapToInt(g -> g.reduce((a, b) -> a*b).orElse(0))
                .sum()
        );

        System.err.println(
            new GearMap(
                new InputFileLines("/day3.txt")
                    .lines()
                    .toList())
                .realGears()
                .mapToInt(g -> g.reduce((a, b) -> a*b).orElse(0))
                .sum()
        );
    }

    public record GearMap(Collection<PartNumber> partNumbers, Collection<Coordinate> symbols){
        public GearMap(List<String> lines){
            this(IntStream.range(0, lines.size())
                .mapToObj(i -> new AsterixLine(lines.get(i), i))
                .toList());
        }

        public GearMap(Collection<AsterixLine> lines){
            this(
                lines.stream()
                    .map(AsterixLine::partNumbers)
                    .flatMap(Collection::stream)
                    .toList(),
                lines.stream()
                    .map(AsterixLine::symbols)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet()));
        }

        public Stream<IntStream> realGears(){
            return symbols.stream()
                .map(s -> partNumbers.stream()
                    .filter(p -> p.hasSymbol(s))
                    .map(PartNumber::value)
                    .toList()
                )
                .filter(v -> v.size() == 2)
                .map(v -> v.stream().mapToInt(Integer::valueOf));
        }
    }

    public static class AsterixLine extends Line{
        private final static Pattern SYMBOL_PATTERN = Pattern.compile("(\\*)");

        public AsterixLine(String line, Integer y) {
            super(line, y);
        }

        @Override
        public Pattern symbolPattern() {
            return SYMBOL_PATTERN;
        }
    }
}
