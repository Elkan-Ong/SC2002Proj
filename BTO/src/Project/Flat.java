package Project;

import java.util.ArrayList;
import java.util.List;


public class Flat implements AvailableFlatTypes {
    private List<Unit> units = new ArrayList<>();
    private long price;
    private String type;
    private int noOfUnits;
    private int bookedUnits = 0;

    public Flat(String type, int noOfUnits, long price) {
        this.type = type;
        this.price = price;
        this.noOfUnits = noOfUnits;
        for (int i = 1; i <= this.noOfUnits; i++) {
            units.add(new Unit(i));
        }
    }

    public List<Unit> getUnits() {
        return units;
    }

    public int getBookedUnits() {
        return bookedUnits;
    }

    public long getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) { this.type = type; }

    public int getNoOfUnits() { return noOfUnits; }

    public void setNoOfUnits(int units) { this.noOfUnits = units; }

    public void addUnits(int units) {
        for (int i=this.units.size(); i <= units+this.units.size(); i++) {
            this.units.add(new Unit(i));
        }
        System.out.println(units + " units added");
    }

    public void removeUnits(int units) {
        for (int i=0; i < units; i++) {
            this.units.removeLast();
        }
        System.out.println(units + " units removed");
    }

    public void reserveUnit() { this.bookedUnits++; }

    public void returnUnit() { this.bookedUnits--; }

    public void displayFlat() {
        System.out.println(type + " Flat information;");
        System.out.println("Total no. of units: " + units.size());
        System.out.println("Price of unit: " + price);
        System.out.println("Current no. of available units: " + noOfUnits);
    }
}
