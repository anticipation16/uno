package model;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a set of UNO Cards.
 */
public final class CardSet {
    private final Set<Card> cards = new HashSet<>();

    public void add(Card card) {
        cards.add(card);
    }

    public boolean contains(Card card) {
        return cards.contains(card);
    }

    public void remove(Card card) {
        cards.remove(card);
    }

    public int size() {
        return cards.size();
    }

    public boolean isEmpty() {
        return size() == 0;
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
