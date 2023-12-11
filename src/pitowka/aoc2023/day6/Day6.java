package pitowka.aoc2023.day6;

import java.math.BigInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day6 {
    public static void main(String[] args) {

        System.err.println(
            Stream.of(
                    new Race(48, 261),
                    new Race(93, 1192),
                    new Race(84, 1019),
                    new Race(66, 1063))
                .map(Race::waysToBeat)
                .reduce(Math::multiplyExact)
                .orElseThrow()
        );

        System.err.println(
            new Race(48938466, "261119210191063").waysToBeat()
        );
    }

    private static class Race {
        private final Integer time;
        private final BigInteger distance;

        public Race(Integer time, String distance) {
            this(time, new BigInteger(distance));
        }

        public Race(Integer time, Integer distance) {
            this(time, new BigInteger(distance.toString()));
        }

        public Race(Integer time, BigInteger distance) {
            this.time = time;
            this.distance = distance;
        }

        public Integer waysToBeat(){
            return Math.toIntExact(
                IntStream.range(1, time)
                    .mapToObj(t -> new BigInteger(String.valueOf(time - t))
                        .multiply(new BigInteger(String.valueOf(t))))
                    .filter(d -> d.compareTo(distance) > 0)
                    .count());
        }
    }
}
