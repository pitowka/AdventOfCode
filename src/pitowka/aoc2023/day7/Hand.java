package pitowka.aoc2023.day7;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Hand implements Comparable<Hand>{
    private final List<Card> cards;
    private final Integer bid;

    public Hand(String cards){
        this(cards.split(" ")[0], Integer.valueOf(cards.split(" ")[1]));
    }
    public Hand(String cards, Integer bid){
        this(cards.chars()
                .mapToObj(c -> Card.ofCharacter((char)c))
                .toList(),
            bid);
    }

    public Hand(List<Card> cards, Integer bid) {
        this.cards = cards;
        this.bid = bid;
    }

    @Override
    public int compareTo(Hand o) {
        int retVal = -ranking().compareTo(o.ranking());

        if(retVal == 0){
            return fineRanking().compareTo(o.fineRanking());
        }else{
            return retVal;
        }
    }

    private HandValue ranking(){
        return HandValue.handValue(cards);
    }

    private Integer fineRanking(){
        return Integer.parseInt(cards.stream()
            .map(Card::hex)
            .collect(Collectors.joining("")), 16);
    }

    public Integer winning(Integer ranking){
        return Math.multiplyExact(ranking, bid);
    }

    @Override
    public String toString() {
        return "Hand{" +
            "cards=" + cards +
            '}';
    }
}
