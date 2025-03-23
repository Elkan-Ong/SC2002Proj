package Project;

import Users.HDBManager;

import java.util.Date; // Requires some testing

public class HDBProject {
    private HDBManager manager;
    private String name;
    private String neighbourhood;
    private String flatType; // ! Will become Project.Flat Object
    private int units;
    private Date openingDate;
    private Date closingDate;
    private int availableOfficerSlots = 10;

    // TODO
    public HDBProject() {

    }

}
