package Project;

import Users.HDBManager;
import Users.HDBOfficer;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

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
    private boolean visible = false;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");

    // Constructor for file reading
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
    }

    // Constructor for new project creation
    public HDBProject(String projectName, String neighbourhood,
                      String type1, int units1, long price1,
                      String type2, int units2, long price2,
                      Date openingDate, Date closingDate,
                      HDBManager manager, int officerSlots) {
        this.name = projectName;
        this.neighbourhood = neighbourhood;
        this.flatType.add(new Flat(type1, units1, price1));
        this.flatType.add(new Flat(type2, units2, price2));
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.manager = manager;
        this.availableOfficerSlots = officerSlots;
    }

    public void displayProject() {
        System.out.println("Project Information: ");
        System.out.println("Name: " + name);
        System.out.println("Neighbourhood: " + neighbourhood);
        for (Flat flat : flatType) {
            flat.displayFlat();
        }
        System.out.println("Opening Date: " + openingDate);
        System.out.println("Closing Date: " + closingDate);
        System.out.println("Manager: " + manager.getName());
        System.out.println("Officer Slots: " + availableOfficerSlots);
        // TODO
//        System.out.println("Assigned Officers: ");
//        for (HDBOfficer officer : assignedOfficers) {
//            officer.getName();
//        }

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public ArrayList<Flat> getFlatType() {
        return this.flatType;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public void toggleVisibility() {
        this.visible = !this.visible;
        System.out.println("Project is now " + (this.visible ? "visible" : "invisible"));
    }

    public String selectAvailableFlats() {
        Scanner sc = new Scanner(System.in);
        ArrayList<Flat> availableFlats = new ArrayList<Flat>();
        for (Flat flat : flatType) {
            if (flat.getNoOfUnitsAvailable() >= 1) {
                availableFlats.add(flat);
            }
        }
        if (availableFlats.isEmpty()) {
            System.out.println("No units are available");
            return null;
        }
        System.out.println("Select Flat Type:");
        for (int i=0; i < availableFlats.size(); i++) {
            System.out.println((i+1) + ") " + flatType.get(i).getType() + ": " + flatType.get(i).getUnits() + " available");
        }
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                if (choice < 1 || choice > availableFlats.size()) {
                    System.out.println("Invalid Selection!");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid Selection!");
            }
        }
        availableFlats.get(choice-1).reserveUnit();
        return availableFlats.get(choice-1).getType();
    }


}
