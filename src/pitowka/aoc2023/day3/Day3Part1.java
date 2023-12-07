package pitowka.aoc2023.day3;

import pitowka.aoc.InputFileLines;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day3Part1 {

    public static void main(String[] args) {
        System.err.println(
            new PartMap(
                new InputFileLines("/day3.txt")
                    .lines()
                    .toList())
                .realPartNumbers().stream()
                .mapToInt(Integer::valueOf)
                .sum()
        );
    }

    public record PartMap(Collection<PartNumber> partNumbers, Collection<Coordinate> symbols){
        public PartMap(List<String> lines){
            this(IntStream.range(0, lines.size())
                .mapToObj(i -> new AllSymbolsLine(lines.get(i), i))
                .toList());
        }

        public PartMap(Collection<AllSymbolsLine> lines){
            this(
                lines.stream()
                    .map(AllSymbolsLine::partNumbers)
                    .flatMap(Collection::stream)
                    .toList(),
                lines.stream()
                    .map(AllSymbolsLine::symbols)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toSet()));
        }

        public Collection<String> realPartNumbers(){
            return partNumbers.stream()
                .filter(pn -> pn.surroundings().stream().anyMatch(symbols::contains))
                .map(PartNumber::value)
                .toList();
        }
    }

    public static class AllSymbolsLine extends Line{
        private final static Pattern SYMBOL_PATTERN = Pattern.compile("([^.\\d])");

        public AllSymbolsLine(String line, Integer y) {
            super(line, y);
        }

        @Override
        public Pattern symbolPattern() {
            return SYMBOL_PATTERN;
        }
    }
}
