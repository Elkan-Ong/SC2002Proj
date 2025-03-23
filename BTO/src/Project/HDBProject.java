package Project;

import Enums.RoomType;
import Users.HDBManager;

import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HDBProject {
    private HDBManager manager;
    private String name;
    private String neighbourhood;
    private ArrayList<Flat> flatType = new ArrayList<Flat>();
    private int units = 0;
    private Date openingDate;
    private Date closingDate;
    private int availableOfficerSlots = 10;
    private SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");

    // TODO
    public HDBProject(String[] values) throws ParseException {
        this.name = values[0];
        this.neighbourhood = values[1];
        this.flatType.add(new Flat(values[2], Integer.parseInt(values[4]), Integer.parseInt(values[3])));
        this.flatType.add(new Flat(values[5], Integer.parseInt(values[7]), Integer.parseInt(values[6])));
        for (Flat flat : flatType) {
            this.units += flat.getUnits().size();
        }
        this.openingDate = format.parse(values[8]);
        this.closingDate = format.parse(values[9]);
    }

    private static Flat getFlatType(String type, int price, int noOfUnitsAvailable) {

    }



}
