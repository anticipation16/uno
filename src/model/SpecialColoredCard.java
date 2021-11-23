package model;

import java.util.Objects;

/**
 * Represents valid special colored cards - Draw2, Reverse, Skip
 */
public class SpecialColoredCard extends ColoredCard {

    private final Speciality speciality;

    public SpecialColoredCard(Color color, Speciality speciality) {
        super(color);
        this.speciality = speciality;
    }

    public Speciality getSpeciality() {
        return speciality;
    }


    @Override
    public String toString() {
        return getColor().toString().charAt(0) + ":" + speciality.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecialColoredCard that = (SpecialColoredCard) o;
        return speciality == that.speciality && this.getColor() == that.getColor();
    }

    @Override
    public int hashCode() {
        return Objects.hash(speciality, getColor());
    }
}
