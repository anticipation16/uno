package model;

import java.util.ArrayList;
import java.util.List;

public class CardUtility {
    public static List<Card> getAllCardsOfColor(Color color) {
        List<Card> cardsOfColor = new ArrayList<>();
        final int UNO_MIN_NUMBER = 0;
        final int UNO_MAX_NUMBER = 9;

        for (int i = UNO_MIN_NUMBER; i <= UNO_MAX_NUMBER; i++) {
            Card c = new NumberedCard(color, i);
            cardsOfColor.add(c);
        }

        cardsOfColor.add(new SpecialColoredCard(color, Speciality.DRAW2));
        cardsOfColor.add(new SpecialColoredCard(color, Speciality.SKIP));
        cardsOfColor.add(new SpecialColoredCard(color, Speciality.REVERSE));

        return cardsOfColor;
    }

}
