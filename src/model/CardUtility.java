package model;

import exceptions.IllegalCardStringException;

import java.util.*;

import static model.Color.*;

/**
 * Performs useful operations related to cards in the game.
 */
public class CardUtility {
    private static final Map<String, Color> stringColorHashMap = new HashMap<>();
    public static final int UNO_MIN_NUMBER = 0;
    public static final int UNO_MAX_NUMBER = 9;

    private CardUtility() {
    }

    static {
        stringColorHashMap.put("Y", YELLOW);
        stringColorHashMap.put("G", GREEN);
        stringColorHashMap.put("B", BLUE);
        stringColorHashMap.put("R", RED);
    }

    /**
     * Returns all possible cards of {@code color} that are present in a standard uno deck.
     *
     * @param color The color whose all cards are needed
     * @return all possible cards of {@code color} from a standard uno deck.
     */
    public static List<Card> getAllCardsOfColorInUNODeck(Color color) {
        List<Card> cardsOfColor = new ArrayList<>();

        for (int i = UNO_MIN_NUMBER; i <= UNO_MAX_NUMBER; i++) {
            Card c = new NumberedCard(color, i);
            cardsOfColor.add(c);
            if (i > UNO_MIN_NUMBER)
                cardsOfColor.add(c); // cards from 1 are present twice for each set
        }

        for (int i = 0; i < 2; i++) {
            cardsOfColor.add(new SpecialColoredCard(color, Speciality.DRAW2));
            cardsOfColor.add(new SpecialColoredCard(color, Speciality.SKIP));
            cardsOfColor.add(new SpecialColoredCard(color, Speciality.REVERSE));
        }

        return cardsOfColor;
    }

    // Todo: eliminate code smell using a better design pattern
    // https://stackoverflow.com/questions/70073456/how-to-avoid-instanceof-and-dynamic-getter-check

    public static boolean areBothColoredAndHaveSameColor(Card c1, Card c2) {
        if (c1 instanceof ColoredCard coloredCard1 && c2 instanceof ColoredCard coloredCard2)
            return coloredCard1.getColor() == coloredCard2.getColor();
        return false;
    }

    public static boolean areBothNumberedAndHaveSameNumber(Card c1, Card c2) {
        if (c1 instanceof NumberedCard numberedCard1 && c2 instanceof NumberedCard numberedCard2)
            return numberedCard1.getNumber() == numberedCard2.getNumber();
        return false;
    }

    public static boolean areBothSpecialAndHaveSameSpeciality(Card c1, Card c2) {
        if (c1 instanceof SpecialColoredCard specialColoredCard1 && c2 instanceof SpecialColoredCard specialColoredCard2)
            return specialColoredCard1.getSpeciality() == specialColoredCard2.getSpeciality();
        return false;
    }

    /**
     * Returns card from a string of form [Letter - R/G/B/Y]:[Number 0-9] or [Letter - R/G/B/Y]:[Speciality]
     * Examole: R:9 will return a red card with number 9
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
            if (!Character.isDigit(str.charAt(1))) throw new IllegalCardStringException(
                    str.charAt(0) + " is not a number"
            );
            int n = Integer.parseInt(str.substring(1));
            return new NumberedCard(c, n);
        } else try {
            Speciality speciality = Speciality.valueOf(str.substring(1));
            return new SpecialColoredCard(c, speciality);
        } catch (IllegalArgumentException e) {
            throw new IllegalCardStringException(str.substring(1) + " is not a valid speciality");
        }
    }
}
