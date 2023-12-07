package pitowka.aoc2023.day3;

import java.util.Collection;
import java.util.regex.Pattern;

public abstract class Line {
    private final static Pattern PART_NUMBER_PATTERN = Pattern.compile("(\\d+)");


    private final String line;
    private final  Integer y;

    public Line(String line, Integer y) {
        this.line = line;
        this.y = y;
    }


    public Collection<PartNumber> partNumbers() {
        return PART_NUMBER_PATTERN.matcher(line)
            .results()
            .map(m -> new PartNumber(m.group(), new Coordinate(m.start(), y)))
            .toList();
    }

    public abstract Pattern symbolPattern();
    public Collection<Coordinate> symbols() {
        return symbolPattern().matcher(line)
            .results()
            .map(m -> new Coordinate(m.start(), y))
            .toList();
    }
}
