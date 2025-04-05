package Project;

import Misc.Query;
import Misc.WithdrawApplication;
import Users.Applicant;
import Users.HDBManager;
import Users.HDBOfficer;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class HDBProject {
    private HDBManager manager;
    private String name;
    private String neighbourhood;
    private List<Flat> flatType = new ArrayList<>();
    private int units = 0;
    private Date openingDate;
    private Date closingDate;
    private List<HDBOfficer> assignedOfficers = new ArrayList<>();
    private int availableOfficerSlots;
    private boolean visible = true; // TODO change to false after testing
    List<ProjectApplication> projectApplications = new ArrayList<>();
    List<WithdrawApplication> withdrawals = new ArrayList<>();
    List<Query> queries = new ArrayList<>();

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    // Constructor for file reading
    public HDBProject(String[] values, HDBManager projectManager, List<HDBOfficer> projectOfficers) throws ParseException {
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

    public int getAvailableOfficerSlots() {
        return availableOfficerSlots;
    }

    public void setAvailableOfficerSlots(int slots) {
        this.availableOfficerSlots = slots;
    }

    public List<HDBOfficer> getAssignedOfficers() {
        return assignedOfficers;
    }

    public void addQuery(Query query) {
        this.queries.add(query);
    }

    public List<Query> getQueries() { return queries; }

    public void displayProjectApplicant() {
        System.out.println("Project Information: ");
        System.out.println("Name: " + name);
        System.out.println("Neighbourhood: " + neighbourhood);
        for (Flat flat : flatType) {
            flat.displayFlat();
        }
        System.out.println("Opening Date: " + openingDate);
        System.out.println("Closing Date: " + closingDate);
        System.out.println("Manager: " + manager.getName());
    }

    // TODO displayProject for Officer/Manager should display officer slots also
    // TODO can do manager special one with current officers also

    public void displayProjectStaff() {
        displayProjectApplicant();
        System.out.println("Visibility: " + (visible ? "Visible" : "Invisible"));
        // TODO display officer when implemented
    }

    public String getName() {
        return this.name;
    }

    public String getNeighbourhood() { return this.neighbourhood; }

    public void setName(String name) {
        this.name = name;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public List<Flat> getFlatType() {
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

    public HDBManager getManager() { return this.manager; }

    public boolean getVisibility() { return visible; }

    public List<ProjectApplication> getAllProjectApplications() { return projectApplications; }

    public List<WithdrawApplication> getWithdrawals() { return withdrawals; }

    public void addApplication(ProjectApplication application) {
        projectApplications.add(application);
    }

    public void addWithdrawal(WithdrawApplication withdrawal) {
        withdrawals.add(withdrawal);
    }

    public void toggleVisibility() {
        this.visible = !this.visible;
        System.out.println("Project is now " + (this.visible ? "visible" : "invisible"));
    }

    public Flat selectAvailableFlats(Applicant applicant) {
        Scanner sc = new Scanner(System.in);
        List<Flat> availableFlats = new ArrayList<>();
        for (Flat flat : flatType) {
            if (flat.getNoOfUnits() >= 1) {
                availableFlats.add(flat);
            }
        }
        if (availableFlats.isEmpty()) {
            System.out.println("No units are available");
            return null;
        }
        System.out.println("Select Flat Type:");
        for (int i=0; i < availableFlats.size(); i++) {
            System.out.println((i+1) + ") " + flatType.get(i).getType() + ": " + (flatType.get(i).getNoOfUnits() - flatType.get(i).getBookedUnits()) + " available");
        }
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                if (choice < 1 || choice > availableFlats.size()) {
                    System.out.println("Invalid Selection!");
                    continue;
                }
                if (availableFlats.get(choice-1).getType().equals("3-Room") && applicant.getMaritalStatus().equals("Single")) {
                    System.out.println("As a single applicant, you may not apply for any other flats than 2-Room");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid Selection!");
            }
            sc.nextLine();
        }
        return availableFlats.get(choice-1);
    }


}
