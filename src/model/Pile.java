package model;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Pile {
    private final Stack<Card> cards = new Stack<>();

    public Pile() {
    }

    public Pile(List<Card> cardList) {
        cards.addAll(cardList);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card popTopCard() {
        return cards.pop();
    }

    public Card peekTopCard() {
        return cards.peek();
    }

    public void addCard(Card card) {
        cards.add(card);
    }
}
