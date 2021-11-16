package model;

public abstract class ColoredCard implements Card {
    private final Color color;

    public Color getColor() {
        return color;
    }

    public ColoredCard(Color color) {
        this.color = color;
    }

}
