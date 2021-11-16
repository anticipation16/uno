package model;

import java.util.HashSet;

public class CardSet {
    HashSet<Card> cards = new HashSet<>();

    public CardSet() {

    }

    public void add(Card card) {
        cards.add(card);
    }

    @Override
    public String toString() {
        return cards.toString();
    }
}
