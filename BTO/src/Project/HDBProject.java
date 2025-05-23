package Project;

import Misc.Query;
import Misc.OfficerRegistration;
import Misc.WithdrawApplication;
import Project.ProjectInterfaces.HDBProjectDisplay;
import Users.HDBManager;
import Users.HDBOfficer;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * A BTO Project that was created by a Manager
 * Each Project will have 2 flat types (as of now 2-Room and 3-Room only)
 */
public class HDBProject implements HDBProjectDisplay {
    /**
     * The Manager who is managing the project
     */
    private final HDBManager manager;

    /**
     * Name of the project
     */
    private String name;

    /**
     * The neighbourhood the project will be built in
     */
    private String neighbourhood;

    /**
     * A List of Flat objects which contain information on the flats of the project
     */
    private final List<Flat> flatType = new ArrayList<>();

    /**
     * The date the project is open for applications
     */
    private Date openingDate;

    /**
     * The date the project closes and can no longer be applied for
     */
    private Date closingDate;

    /**
     * No. of slots for officers to be assigned to this Project
     */
    private int availableOfficerSlots;

    /**
     * HDB Officers assigned to assist the Manager
     */
    private List<HDBOfficer> assignedOfficers = new ArrayList<>();

    /**
     * Whether the project is visible to Applicants
     */
    private boolean visible = true;

    /**
     * List containing all the Applications for this project
     */
    List<ProjectApplication> projectApplications = new ArrayList<>();

    /**
     * List containing all the Withdrawals for this project
     */
    List<WithdrawApplication> withdrawals = new ArrayList<>();

    /**
     * List containing all the Query for this project
     */
    List<Query> queries = new ArrayList<>();

    /**
     * The number of Officers that can be assigned to this project
     */
    List<OfficerRegistration> officerApplications = new ArrayList<>();
    List<ProjectApplication> applicationsPendingBooking = new ArrayList<>();

    /**
     * Constructor for reading csv files
     * @param values values in the csv file
     * @param projectManager Manager managing this project
     * @param projectOfficers Officers assigned to this project
     */
    public HDBProject(String[] values, HDBManager projectManager, List<HDBOfficer> projectOfficers) throws ParseException {
        this.name = values[0];
        this.neighbourhood = values[1];
        this.flatType.add(new Flat(values[2], Integer.parseInt(values[4]), Integer.parseInt(values[3])));
        this.flatType.add(new Flat(values[5], Integer.parseInt(values[7]), Integer.parseInt(values[6])));
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
            this.openingDate = format.parse(values[8]);
            this.closingDate = format.parse(values[9]);
        } catch (ParseException pe) {
            System.err.println("Parse error in file ProjectList.csv with data: " + String.join(",", values));
        }
        this.manager = projectManager;
        this.assignedOfficers = projectOfficers;
        this.availableOfficerSlots = Integer.parseInt(values[11]);
    }

    /**
     * Creates a new HDB Project after Manager fills in project form
     * @param projectName Name of the Project
     * @param neighbourhood The Neighbourhood the Project is in
     * @param type1 First type of Flat
     * @param units1 No. of units in the first Flat type
     * @param price1 Price of the first Flat type
     * @param type2 Second type of Flat
     * @param units2 No. of units in the second Flat type
     * @param price2 Price of the second Flat type
     * @param openingDate Opening Date of the Project
     * @param closingDate Closing Date of the Project
     * @param manager Manager managing the Project
     * @param officerSlots No. of Officers that can be assigned to this Project
     */
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

    /**
     * Gets the number of Officers that can be assigned to this Project
     * @return number of Officers that can be assigned to this Project
     */
    public int getAvailableOfficerSlots() {
        return availableOfficerSlots;
    }

    /**
     * Sets the number of Officers that can be assigned to this Project
     * @param slots number of Officers that can be assigned to this Project
     */
    public void setAvailableOfficerSlots(int slots) {
        this.availableOfficerSlots = slots;
    }


    /**
     * Gets a list of Officers that have been assigned to this Project
     * @return list of Officers that have been assigned to this Project
     */
    public List<HDBOfficer> getAssignedOfficers() {
        return assignedOfficers;
    }

    public void assignOfficer(HDBOfficer officer) { assignedOfficers.add(officer); }

    /**
     * Adds a Query to the list of queries in the Project
     * @param query Query to be added
     */
    public void addQuery(Query query) {
        this.queries.add(query);
    }

    /**
     * gets the list of Query for this Project
     * @return List of Query
     */
    public List<Query> getQueries() { return queries; }

    /**
     * Gets the name of the Project
     * @return Name of the Project
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the neighbourhood of the Project
     * @return neighbourhood of the Project
     */
    public String getNeighbourhood() { return this.neighbourhood; }

    /**
     * Changes the name of the Project
     * @param name new name of the Project
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Changes the neighbourhood of the Project
     * @param neighbourhood new neighbourhood of the Project
     */
    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    /**
     * Gets the List of Flats
     * @return List of Flats
     */
    public List<Flat> getFlatType() {
        return this.flatType;
    }

    /**
     * Gets opening date of the Project
     * @return opening date of the Project
     */
    public Date getOpeningDate() {
        return openingDate;
    }

    /**
     * Change the opening date of the Project
     * @param openingDate new opening date
     */
    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    /**
     * Gets the closing date of the Project
     * @return closing date of the Project
     */
    public Date getClosingDate() {
        return closingDate;
    }

    /**
     * Changes the closing date of the Project
     * @param closingDate new closing date
     */
    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    /**
     * Gets the Manager managing the Project
     * @return Manager managing the Project
     */
    public HDBManager getManager() { return this.manager; }

    /**
     * Gets the visibility of the Project
     * @return Visibility of the Project
     */
    public boolean getVisibility() { return visible; }

    /**
     * Changes visibility of Project
     * @param visibility to set the Project to
     */
    public void setVisible(boolean visibility) {
        this.visible = visibility;
    }

    /**
     * Gets a List of all the Applications made for this Project
     * @return List of all the Applications made for this Project
     */
    public List<ProjectApplication> getAllProjectApplications() { return projectApplications; }

    /**
     * Gets a List of all the Withdrawals made for this Project
     * @return List of all the Withdrawals made for this Project
     */
    public List<WithdrawApplication> getWithdrawals() { return withdrawals; }

    /**
     * Adds an Application to the List of all Applications for this Project
     * @param application Application to be added
     */
    public void addApplication(ProjectApplication application) {
        projectApplications.add(application);
    }

    /**
     * Gets the list of OfficerRegistrations made to this Project
     * @return list of OfficerRegistrations made to this Project
     */
    public List<OfficerRegistration> getOfficerApplications() { return officerApplications; }

    /**
     * Adds an OfficerRegistration to the list of registrations made to this project
     * @param registration registration to be added to the list
     */
    public void addOfficerRegistration(OfficerRegistration registration) {
        officerApplications.add(registration);
    }

    /**
     * Adds a Withdrawal to the List of all Withdrawals for this Project
     * @param withdrawal Withdrawal to be added
     */
    public void addWithdrawal(WithdrawApplication withdrawal) {
        withdrawals.add(withdrawal);
    }

    /**
     * Gets the list of applications that pending booking
     * @return list of applications that are pending booking
     */
    public List<ProjectApplication> getAllApplicationsPendingBooking() { return applicationsPendingBooking; }

    /**
     * Adds an Application that has been approved to the list of applications that want to get a booking
     * Request is made by Applicant after their application has been approved
     * @param application Application that wants to get a booking
     */
    public void addApplicationPendingBooking(ProjectApplication application) { applicationsPendingBooking.add(application); }

    /**
     * Toggles the visibility of the project from visible to invisible vice versa
     */
    public void toggleVisibility() {
        this.visible = !this.visible;
        System.out.println("Project is now " + (this.visible ? "visible" : "invisible"));
    }

    /**
     * Displays information of the Project to be used by Applicants
     */
    @Override
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
        System.out.println();
    }


    /**
     * Displays information of the Project, to be used by HDB Staff
     */
    @Override
    public void displayProjectStaff() {
        displayProjectApplicant();
        System.out.println("Visibility: " + (visible ? "Visible" : "Invisible"));
        System.out.println("Officers Assigned: ");
        for (HDBOfficer officer : assignedOfficers) {
            System.out.println(officer.getName());
        }
        System.out.println();
    }



}
