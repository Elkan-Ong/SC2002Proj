package Project;

import Users.Applicant;

import java.util.ArrayList;


public class Flat {
    private ArrayList<Unit> units = new ArrayList<Unit>();
    private long price;
    private String type;
    private int noOfUnitsAvailable;

    public Flat(String type, int noOfUnitsAvailable, long price) {
        this.type = type;
        this.price = price;
        this.noOfUnitsAvailable = noOfUnitsAvailable;
        for (int i=1; i <= this.noOfUnitsAvailable; i++) {
            units.add(new Unit(i));
        }
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public long getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public int getNoOfUnitsAvailable() { return noOfUnitsAvailable; }

    public void reserveUnit() { this.noOfUnitsAvailable--; }

    public void returnUnit() { this.noOfUnitsAvailable++; }

    public void assignUnit(Applicant applicant) {
        // Get first available unit
        for (int i =0; i < units.size(); i++) {
            if (!units.get(i).getBooked()) {
                units.get(i).setBooked(applicant);

                // Update applicants profile with type of flat booked
                applicant.setBookedUnit(units.get(i));
                break;
            }
        }
    }

    public void displayFlat() {
        System.out.println(type + " Flat information;");
        System.out.println("Total no. of units: " + units.size());
        System.out.println("Price of unit: " + price);
        System.out.println("Current no. of available units: " + noOfUnitsAvailable);
    }
}
