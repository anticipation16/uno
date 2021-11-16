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
}
