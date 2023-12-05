package pitowka.aoc;

import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class InputFileLines {
    private final String resourcePath;

    public InputFileLines(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public Stream<String> lines(){
        return new Scanner(
            Objects.requireNonNull(
                InputFileLines.class.getResourceAsStream(resourcePath)))
                .useDelimiter("\n")
            .tokens();
    }
}
