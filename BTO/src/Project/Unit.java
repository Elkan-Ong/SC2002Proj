package Project;

import Users.Applicant;

/**
 * A unit in HDB Flat of a specific room type
 * @author Elkan Ong Han'en
 * @since 2025-4-5
 */
public class Unit {
    /**
     * Applicant that booked the unit
     */
    private Applicant bookedBy = null;

    /**
     * Identifier of the unit
     */
    private int unitID;

    /**
     * Indicates if the unit has been booked by some Applicant
     */
    private boolean booked = false;


    /**
     * Creates a Unit with and assigns an ID
     * @param unitID unique identifier
     */
    public Unit(int unitID) {
        this.unitID = unitID;
    }

    /**
     * Gets the status of the unit if it is booked
     * @return the units booking status
     */
    public boolean getBooked() {
        return booked;
    }

    /**
     * Changes the booking status of the unit
     * @param applicant applicant that wants to book the unit
     */
    public void setBookedBy(Applicant applicant) {
        this.booked = true;
        this.bookedBy = applicant;
    }

    public void returnUnit() {
        this.booked = false;
        this.bookedBy = null;
    }

    /**
     * Gets the applicant that booked the unit
     * @return applicant that booked the unit
     */
    public Applicant getBookedBy() { return bookedBy; }

    /**
     * Gets the unitID
     * @return the unitID of the unit
     */
    public int getUnitID() { return unitID; }
}
