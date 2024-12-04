package pitowka.aoc2024.day1;

import pitowka.aoc.InputFileLines;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day1 {
    public static void main(String[] args){

        System.err.println(
            new Pairs(
                new InputFileLines("/aoc2024/day1.txt")
                    .lines()
                    .map(Pair::new)
                    .toList()
            ).distance()
        );

        System.err.println(
            new Pairs(
                new InputFileLines("/aoc2024/day1.txt")
                    .lines()
                    .map(Pair::new)
                    .toList()
            ).similarity()
        );
    }

    record Pair(Integer left, Integer right){
        Pair(String line){
            this(
            Integer.valueOf(line.split("\\s+")[0]),
            Integer.valueOf(line.split("\\s+")[1]));
        }
    }

    record Pairs(List<Pair> pairs){
        private List<Integer> left(){
            return sortedSide(Pair::left);
        }

        private List<Integer> right(){
            return sortedSide(Pair::right);
        }

        private Map<Integer, Long> rightQuantity(){
            return right().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        }

        private List<Integer> sortedSide(Function<? super Pair, Integer> sideFunction){
            return pairs.stream()
                    .map(sideFunction)
                    .sorted()
                    .toList();
        }

        Integer distance() {
            int distance = 0;
            for(int i = 0; i < pairs.size(); i++){
                distance += Math.abs(left().get(i) - right().get(i));
            }

            return distance;
        }

        Long similarity(){
            return left().stream()
                    .mapToLong(l -> l * rightQuantity().getOrDefault (l, 0L))
                    .sum();
        }
    }
}
