package model;

import exceptions.IllegalCardStringException;

import java.util.*;

public class CardUtility {
    private static final Map<String, Color> stringColorHashMap = new HashMap<>();

    static {
        stringColorHashMap.put("Y", Color.YELLOW);
        stringColorHashMap.put("G", Color.GREEN);
        stringColorHashMap.put("B", Color.BLUE);
        stringColorHashMap.put("R", Color.RED);
    }

    public static List<Card> getAllCardsOfColor(Color color) {
        List<Card> cardsOfColor = new ArrayList<>();
        final int UNO_MIN_NUMBER = 1;
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

    /**
     * Returns card from a string of form [Letter - R/G/B/Y]:[Number 0-9] or [Letter - R/G/B/Y]:[Speciality]
     *
     * @param str string representation of the card
     * @return a card object from the string
     */
    public static Card stringRepToCard(String str) throws IllegalCardStringException {
        Color c = Optional.ofNullable(stringColorHashMap.get(str.substring(0, 1)))
                .orElseThrow
                        (
                                () -> new IllegalCardStringException(
                                        str.charAt(0) + " is not a valid color abbreviation")
                        );

        if (str.length() == 2) {
            if (!Character.isDigit(str.charAt(1))) {
                throw new IllegalCardStringException(
                        str.charAt(0) + " is not a number");
            }
            int n = Integer.parseInt(str.substring(1));
            return new NumberedCard(c, n);
        } else {
            try {
                Speciality speciality = Speciality.valueOf(str.substring(1));
                return new SpecialColoredCard(c, speciality);
            } catch (IllegalArgumentException e) {
                throw new IllegalCardStringException(str.substring(1) + " is not a valid speciality");
            }

        }
    }
}
