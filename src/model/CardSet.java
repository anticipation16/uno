package model;

import java.util.HashSet;
import java.util.Set;

public class CardSet {
    Set<Card> cards = new HashSet<>();

    public CardSet() {

    }

    public void add(Card card) {
        cards.add(card);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Card card : cards) {
            s.append(card.toString()).append("\n");
        }
        return s.toString();
    }

    public boolean contains(Card card) {
        return cards.contains(card);
    }

    public void remove(Card card) {
        cards.remove(card);
    }

    public int size(){
        return cards.size();
    }


    public static void main(String[] args) {
        Card c = new NumberedCard(Color.YELLOW, 3);
        Card c2 = new NumberedCard(Color.YELLOW, 3);
        CardSet cs = new CardSet();
        cs.add(c);
        System.out.println(cs.contains(c2));
    }
}
