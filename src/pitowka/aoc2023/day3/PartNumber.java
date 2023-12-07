package pitowka.aoc2023.day3;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record PartNumber(String value, Coordinate coordinate) {
    public Collection<Coordinate> surroundings() {
        return Stream.of(
                Stream.of(
                    coordinate.moveX(-1).moveY(-1),
                    coordinate.moveX(-1),
                    coordinate.moveX(-1).moveY(1)
                ),    // first column
                Stream.of(
                    coordinate.moveX(value.length()).moveY(-1),
                    coordinate.moveX(value.length()),
                    coordinate.moveX(value.length()).moveY(1)
                ),     // last column
                coordinate.moveY(-1).rangeX(value.length()),
                coordinate.moveY(1).rangeX(value.length())
            ).flatMap(Function.identity())
            .collect(Collectors.toSet());
    }

    public boolean hasSymbol(Coordinate coordinate){
        return surroundings().contains(coordinate);
    }
}
