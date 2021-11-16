package model;

public abstract class ColoredCard implements Card {
    private Color color;

    public ColoredCard(Color color) {
        this.color = color;
    }

}
