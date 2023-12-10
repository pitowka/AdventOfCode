package pitowka.aoc2023.day5;

import pitowka.aoc.InputFileLines;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day5 {
    public static void main(String[] args) {
        System.err.println(
            new GardenMap(
                new SeedPart2Supplier(),
                new InputFileLines("/day5_example.txt")
                    .asString())
                .locations()
                .min(BigInteger::compareTo)
                .orElseThrow()
        );

        System.err.println(
            new GardenMap(new SeedPart1Supplier(),
                new InputFileLines("/day5.txt")
                    .asString())
                .locations()
                .min(BigInteger::compareTo)
                .orElseThrow()
        );

        System.err.println(
            new GardenMap(new SeedPart2Supplier(),
                new InputFileLines("/day5.txt")
                    .asString())
                .locations()
                .min(BigInteger::compareTo)
                .orElseThrow()
        );
    }

    public static class SeedPart1Supplier implements Function<String, Stream<BigInteger>> {
        private static final Pattern SEEDS_PATTERN = Pattern.compile("(\\d+)");

        @Override
        public Stream<BigInteger> apply(String seeds) {
            return SEEDS_PATTERN.matcher(seeds)
                .results()
                .map(MatchResult::group)
                .map(BigInteger::new);
        }
    }

    public static class SeedPart2Supplier implements Function<String, Stream<BigInteger>>{
        private static final Pattern SEEDS_PATTERN = Pattern.compile("((\\d+)\\s+(\\d+))");

        @Override
        public Stream<BigInteger> apply(String seeds) {
            return SEEDS_PATTERN.matcher(seeds)
                .results()
                .flatMap(mr -> Stream.iterate(
                    new BigInteger(mr.group(2)),
                    i -> i.compareTo(new BigInteger(mr.group(2)).add(new BigInteger(mr.group(3)))) < 0,
                    i -> i.add(new BigInteger("1"))));
        }
    }

    public static class GardenMap {
        private static final Pattern MAPPING_PATTERN = Pattern.compile("(\\d+) (\\d+) (\\d+)");

        private final Stream<BigInteger> seeds;
        private final Function<BigInteger, BigInteger> mappers;

        public GardenMap(Function<String, Stream<BigInteger>>seedsFun, String gardenMap){
            this(seedsFun, Arrays.stream(gardenMap.split("\n\\s*\n")).toList());
        }

        private GardenMap(Function<String, Stream<BigInteger>>seedsFun,List<String> gardenMap){
            this(seedsFun, gardenMap.getFirst(), gardenMap.subList(1, gardenMap.size()));
        }
        private GardenMap(Function<String, Stream<BigInteger>>seedsFun,String seeds, List<String> mappers){
            this(seedsFun.apply(seeds),
                new ChainMapper(
                    mappers.stream()
                        .map(s -> MAPPING_PATTERN.matcher(s)
                            .results()
                            .map(mr -> new Day5.RangeMapper(mr.group(1), mr.group(2), mr.group(3)))
                            .reduce(Day5.RangeMapper::withNext)
                            .orElseThrow()
                        ).toList()));
        }

        public GardenMap(Stream<BigInteger> seeds, Function<BigInteger, BigInteger> mappers) {
            this.seeds = seeds;
            this.mappers = mappers;
        }

        public Stream<BigInteger> locations(){
            return seeds
                .map(mappers);
        }
    }

    private static class RangeMapper implements Function<BigInteger, BigInteger>{
        private final BigInteger dest;
        private final BigInteger source;
        private final BigInteger length;
        private final Function<BigInteger, BigInteger> nextMapper;

        public RangeMapper(String dest, String source, String length) {
            this(new BigInteger(dest), new BigInteger(source), new BigInteger(length));
        }

        public RangeMapper(BigInteger dest, BigInteger source, BigInteger length) {
            this(dest, source,length, Function.identity());
        }
        public RangeMapper(BigInteger dest, BigInteger source, BigInteger length, Function<BigInteger, BigInteger> nextMapper) {
            this.dest = dest;
            this.source = source;
            this.length = length;
            this.nextMapper = nextMapper;
        }

        public RangeMapper withNext(RangeMapper nextMapper){
            return new RangeMapper(nextMapper.dest, nextMapper.source, nextMapper.length, this);
        }

        private boolean isInRange(BigInteger v){
            return source.compareTo(v) <= 0 && v.compareTo(source.add(length)) < 0;
        }

        @Override
        public BigInteger apply(BigInteger v) {
            return isInRange(v) ? dest.add(v).subtract(source) : nextMapper.apply(v);
        }
    }

    private record ChainMapper(Collection<? extends Function<BigInteger, BigInteger>> chainedMappers) implements Function<BigInteger, BigInteger> {

        @Override
            public BigInteger apply(BigInteger v) {
                return chainedMappers.stream()
                    .reduce(v, (r, m) -> m.apply(r), (r1, r2) -> r1);
            }
        }
}
