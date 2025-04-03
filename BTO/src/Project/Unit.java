package Project;

import Users.Applicant;

public class Unit {
    private Applicant bookedBy;
    private int unitID; // Could be specific unit?, alternatively use int and number like in Lab 3
    private boolean booked = false;

    public Unit(int unitID) {
        this.unitID = unitID;
    }

    public boolean getBooked() {
        return booked;
    }

    public void setBooked(Applicant applicant) {
        this.booked = true;
        this.bookedBy = applicant;
    }
}
