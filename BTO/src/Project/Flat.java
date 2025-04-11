package Project;

import Project.ProjectInterfaces.FlatInterface.BookUnitHandler;
import Project.ProjectInterfaces.FlatInterface.FlatDisplay;
import Users.Applicant;

import java.util.ArrayList;
import java.util.List;

/**
 * A Flat in a Project
 * Contains Units to be booked by Applicants after a successful Application
 * @author Elkan Ong Han'en
 * @since 2025-4-6
 */
public class Flat implements AvailableFlatTypes, FlatDisplay, BookUnitHandler {
    /**
     * List of Units belonging to the Flat
     */
    private final List<Unit> units = new ArrayList<>();

    /**
     * Price to buy a Unit in the Flat
     */
    private long price;

    /**
     * Type of Flat e.g. 2-Room, 3-Room
     */
    private String type;

    /**
     * Number of Units in the Flat
     */
    private int noOfUnits;

    /**
     * Number of Units that have already been booked
     */
    private int bookedUnits = 0;

    /**
     * Creates a new Flat of some type, its number of units, and the price per Unit
     * @param type Type of Flat
     * @param noOfUnits Number of Units
     * @param price Price per Unit
     */
    public Flat(String type, int noOfUnits, long price) {
        this.type = type;
        this.price = price;
        this.noOfUnits = noOfUnits;
        for (int i = 1; i <= this.noOfUnits; i++) {
            units.add(new Unit(i));
        }
    }

    /**
     * Gets the List of Units
     * @return List of Units
     */
    public List<Unit> getUnits() {
        return units;
    }

    /**
     * Gets the number of booked Units
     * @return number of booked Units
     */
    public int getBookedUnits() {
        return bookedUnits;
    }

    /**
     * Gets the price per Unit
     * @return price per Unit
     */
    public long getPrice() {
        return price;
    }

    /**
     * Changes the price per Unit
     * @param price new price per Unit
     */
    public void setPrice(long price) {
        this.price = price;
    }

    /**
     * Gets the type of Flat
     * @return type of Flat
     */
    public String getType() {
        return type;
    }

    /**
     * Changes the type of Flat
     * @param type new type of Flat
     */
    public void setType(String type) { this.type = type; }

    /**
     * Gets the number of Units
     * @return number of Units
     */
    public int getNoOfUnits() { return noOfUnits; }

    /**
     * Changes the number of Units
     * @param units new number of Units
     */
    public void setNoOfUnits(int units) { this.noOfUnits = units; }

    /**
     * Adds more Units to the list of Units
     * @param units number of Units to be added
     */
    public void addUnits(int units) {
        for (int i=this.units.size(); i <= units+this.units.size(); i++) {
            this.units.add(new Unit(i));
        }
        System.out.println(units + " units added");
    }

    /**
     * Removes Units from the list of Units
     * @param units number of Units to be removed
     */
    public void removeUnits(int units) {
        for (int i=0; i < units; i++) {
            this.units.removeLast();
        }
        System.out.println(units + " units removed");
    }

    /**
     * Updates the number of booked units
     * Increases it by 1
     */
    public void reserveUnit() { this.bookedUnits++; }

    /**
     * Updates the number of booked units
     * Decreases it by 1
     */
    public void returnUnit() { this.bookedUnits--; }

    /**
     * Display information on the Flat
     */
    @Override
    public void assignUnit(Applicant applicant) {
        // Get first available unit
        for (Unit unit : units) {
            if (!unit.getBooked()) {
                unit.setBookedBy(applicant);
                // Update applicants profile with type of flat booked
                applicant.setBookedUnit(unit);
                break;
            }
        }
    }

    public int getNoOfUnitsAvailable() {
        return noOfUnits - bookedUnits;
    }

    @Override
    public void displayFlat() {
        System.out.println(type + " Flat information;");
        System.out.println("Total no. of units: " + units.size());
        System.out.println("Price of unit: " + price);
        System.out.println("Current no. of available units: " + noOfUnits);
    }
}
