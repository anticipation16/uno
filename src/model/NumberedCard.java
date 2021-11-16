package model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberedCard that = (NumberedCard) o;
        return value == that.value && getColor().equals(that.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, getColor());
    }

}
