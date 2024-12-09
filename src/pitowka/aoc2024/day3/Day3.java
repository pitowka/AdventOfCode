package pitowka.aoc2024.day3;

import pitowka.aoc.InputFileLines;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day3 {
    public static void main(String[] args) {
        System.err.println(
            new InputFileLines("/aoc2024/day3.txt")
                .lines()
                    .mapToInt(l -> Multiplication.MUL_PATTERN.matcher(l)
                        .results()
                        .map(Multiplication::new)
                        .mapToInt(Multiplication::result)
                        .sum())
                    .sum()
        );

        System.err.println(
            new Memory(
                new InputFileLines("/aoc2024/day3.txt")
                    .lines()
                    .flatMap(l -> Multiplication.CONDITIONAL_PATTERN.matcher(l).results()))
                    .result()
        );
    }

    record Multiplication(Integer a, Integer b) implements Instruction{
        static final Pattern MUL_PATTERN = Pattern.compile("(mul\\((\\d{1,3}),(\\d{1,3})\\))");
        static final Pattern CONDITIONAL_PATTERN = Pattern.compile("(mul\\((\\d{1,3}),(\\d{1,3})\\))|(do\\(\\))|(don't\\(\\))");
//1,2,3; do - 4; don't - 5

        Multiplication(MatchResult mr){
            this(Integer.valueOf(mr.group(2)), Integer.valueOf(mr.group(3)));
        }

        @Override
        public Integer result(){
            return a * b;
        }

        @Override
        public Boolean apply(){
            return null;
        }
    }

    record Memory(List<Instruction> muls){
        Memory(Stream<MatchResult> mrs){
            this(mrs
                .map(mr -> mr.group(1) != null
                        ? new Multiplication(mr)
                        : (mr.group(4) != null ? Instruction.DO : Instruction.DO_NOT))
                .toList());
        }

        Integer result(){
            AtomicBoolean apply = new AtomicBoolean(true);

            return muls.stream()
                    .mapToInt(i -> {
                        int retVal = apply.get() ? i.result() : 0;
                        if(i.apply() != null){
                            apply.set(i.apply());
                        }
                        return retVal;
                    })
                .sum();
        }
    }

    interface Instruction{
        Instruction DO = new Instruction() {
            @Override
            public Integer result() {
                return 0;
            }

            @Override
            public Boolean apply() {
                return true;
            }
        };

        Instruction DO_NOT = new Instruction() {
            @Override
            public Integer result() {
                return 0;
            }

            @Override
            public Boolean apply() {
                return false;
            }
        };

        Integer result();
        Boolean apply();
    }
}
