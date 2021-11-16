package model;

public class NumberedCard extends ColoredCard {
    private final int value;
    public NumberedCard(Color color, int value) {
        super(color);
        this.value = value;
    }

    @Override
    public String toString(){
        return getColor().name() + ":" + value;
    }
}
