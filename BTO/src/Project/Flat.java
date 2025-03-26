package Project;

import java.util.ArrayList;


public class Flat {
    private ArrayList<Unit> units = new ArrayList<Unit>();
    private int price;
    private String type;
    private int noOfUnitsAvailable;

    public Flat(String type, int price, int noOfUnitsAvailable) {
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

    public int getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

}
