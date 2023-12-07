package pitowka.aoc2023.day3;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Coordinate(Integer x, Integer y) {
    public Coordinate moveX(Integer diff) {
        return new Coordinate(x + diff, y);
    }

    public Coordinate moveY(Integer diff) {
        return new Coordinate(x, y + diff);
    }

    public Stream<Coordinate> rangeX(Integer length) {
        return IntStream.range(x, x + length)
            .mapToObj(i -> new Coordinate(i, y));
    }
}
