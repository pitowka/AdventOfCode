package pitowka.aoc2023.day7;

import pitowka.aoc.InputFileLines;

import java.util.List;

public class Day7 {
    public static void main(String[] args) {
//        List<Hand> hands = Stream.of(
//                "32T3K 765",
//                "T55J5 684",
//                "KK677 28",
//                "KTJJT 220",
//                "QQQJA 483"
//            ).map(Hand::new)
//            .sorted()
//            .toList();

        List<Hand> hands = new InputFileLines("/aoc2023/day7.txt")
            .lines()
            .map(Hand::new)
            .sorted()
            .toList();

        Integer retVal = 0;
        for(int i = 1; i <= hands.size(); i++){
            retVal += hands.get(i-1).winning(i);
        }

        System.err.println(retVal);
    }

}
