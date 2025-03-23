package Project;

public class Unit {
    private int unitID; // Could be specific unit?, alternatively use int and number like in Lab 3
    private boolean booked = false;

    public Unit(int unitID) {
        this.unitID = unitID;
    }

    public void setBooked() {
        this.booked = true;
    }
}
