package Project;

import Enums.ApplicationStatus;
import Project.ProjectInterfaces.ProjectApplicationDisplay;
import Users.Applicant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An Application that an Applicant creates to indicate their interest in a project
 * Each Applicant can have only one active Application at a time
 */
public class ProjectApplication implements ProjectApplicationDisplay {
    /**
     * Indicates the current status of the Application
     * Possible states: Pending, Unsuccessful, Successful, Booked
     */
    private ApplicationStatus status;
    /**
     * The Applicant who submitted the application
     */
    private final Applicant applicant;
    /**
     * The Project that the Applicant applied for
     */
    private final HDBProject appliedProject;

    /**
     * The type of flat that the Applicant wants e.g. 2-Room, 3-Room
     */
    private final Flat selectedType;

    /**
     * Date application was created
     */
    private final Date creationDate;

    /**
     * Creates an Application with the Applicant, Project, and selected type of flat
     * @param applicant Applicant that submits the application
     * @param project Project the Applicant would like to apply for
     * @param selectedType Type of flat the user wants
     */
    public ProjectApplication(Applicant applicant, HDBProject project, Flat selectedType) {
        this.applicant = applicant;
        this.appliedProject = project;
        this.selectedType = selectedType;
        this.creationDate = new Date();
        this.status = ApplicationStatus.PENDING;
    }

    /**
     * Creates an Application with the Applicant, Project, selected type of flat, and date
     * @param applicant Applicant that submits the application
     * @param project Project the Applicant would like to apply for
     * @param selectedType Type of flat the user wants
     * @param date Date the Application was created
     */
    public ProjectApplication(Applicant applicant, HDBProject project, Flat selectedType, String date) throws ParseException {
        this.applicant = applicant;
        this.appliedProject = project;
        this.selectedType = selectedType;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        this.creationDate = format.parse(date);
    }

    /**
     * Gets the date the application was created
     * @return Date the application was created
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Gets status of the Application
     * @return status of the Application
     */
    public ApplicationStatus getApplicationStatus() {
        return status;
    }

    /**
     * Gets the Project that the Applicant has applied for
     * @return Project that Applicant applied for
     */
    public HDBProject getAppliedProject() { return appliedProject; }

    /**
     * Updates the status of the Application
     * @param status new status of Application
     */
    public void setStatus(ApplicationStatus status) { this.status = status; }

    /**
     * Gets the selected flat type the Applicant wants
     * @return selected flat type of Application
     */
    public Flat getSelectedType() { return selectedType; }

    /**
     * Gets the Applicant who submitted the Application
     * @return Applicant who submitted the Application
     */
    public Applicant getApplicant() { return applicant; }

    /**
     * Displays information of the Application
     */
    @Override
    public void displayApplication() {
        System.out.println("Project Application:");
        System.out.println("Project Name: " + appliedProject.getName());
        System.out.println("Applicant: " + applicant.getName());
        System.out.println("Flat Type: " + selectedType.getType());
        System.out.println("Application Status: " + status);
        if (status == ApplicationStatus.BOOKED) {
            System.out.println("Unit: " + applicant.getBookedUnit().getUnitID());
        }
        System.out.println();
        appliedProject.displayProjectApplicant();
    }

    /**
     * Gets the Applicant's name and the type of unit they want as a String
     * @return Applicant's name and unit they want
     */
    @Override
    public String getApplicationInfo() {
        return applicant.getName() + " " + selectedType.getType();
    }
}
