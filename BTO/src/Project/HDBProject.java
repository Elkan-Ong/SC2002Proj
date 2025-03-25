package Project;

import Enums.RoomType;
import Users.HDBManager;
import Users.HDBOfficer;

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
    private ArrayList<HDBOfficer> assignedOfficers = new ArrayList<HDBOfficer>();
    private int availableOfficerSlots;
    private SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");

    public HDBProject(String[] values, HDBManager projectManager, ArrayList<HDBOfficer> projectOfficers) throws ParseException {
        this.name = values[0];
        this.neighbourhood = values[1];
        this.flatType.add(new Flat(values[2], Integer.parseInt(values[4]), Integer.parseInt(values[3])));
        this.flatType.add(new Flat(values[5], Integer.parseInt(values[7]), Integer.parseInt(values[6])));
        for (Flat flat : flatType) {
            this.units += flat.getUnits().size();
        }
        try {
            this.openingDate = format.parse(values[8]);
            this.closingDate = format.parse(values[9]);
        } catch (ParseException pe) {
            System.err.println("Parse error in file ProjectList.csv with data: " + String.join(",", values));
            pe.printStackTrace();
        }
        this.manager = projectManager;
        this.assignedOfficers = projectOfficers;
        this.availableOfficerSlots = Integer.parseInt(values[11]) - this.assignedOfficers.size();
//        System.out.println("HDB Project Info:");
//        System.out.println("Officers:");
//        System.out.println(assignedOfficers);
//        System.out.println("Manager:");
//        System.out.println(manager + manager.getName());
    }


}
