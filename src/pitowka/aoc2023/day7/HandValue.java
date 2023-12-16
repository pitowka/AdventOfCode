package pitowka.aoc2023.day7;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum HandValue {
    FIVE_OF_KIND,
    FOUR_OF_KIND,    // 2
    FULL_HOUSE,  // 2
    THREE_OF_KIND,   // 3
    TWO_PAIR,    // 3
    ONE_PAIR,    // 4
    HIGH_CARD;


    public static HandValue handValue(List<Card> cards){
        Map<Card, Long> histogram = cards.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        if(histogram.size() == 1){
            return FIVE_OF_KIND;
        }else if(histogram.size() == 5){
            return HIGH_CARD;
        }else if(histogram.size() == 4){
            return ONE_PAIR;
        }else if(histogram.size() == 2){
            if(Set.copyOf(histogram.values()).equals(Set.of(1L, 4L)) ){
                return FOUR_OF_KIND;
            }else{
                return FULL_HOUSE;
            }
        }else if(histogram.size() == 3){
            if(Set.copyOf(histogram.values()).equals(Set.of(1L, 3L)) ){
                return THREE_OF_KIND;
            }else{
                return TWO_PAIR;
            }
        }else {
            throw new RuntimeException();
        }
    }
}
