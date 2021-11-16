package model;

public class SpecialColoredCard extends ColoredCard {
    Speciality speciality;
    public SpecialColoredCard(Color color, Speciality speciality) {
        super(color);
        this.speciality = speciality;
    }
    @Override
    public String toString(){
        return getColor().toString() + ":" + speciality.name();
    }
}
