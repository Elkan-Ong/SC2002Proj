package Users;

import Enums.ApplicationStatus;
import Enums.RegistrationStatus;
import Misc.OfficerRegistration;
import Misc.Query;
import Project.HDBProject;
import Project.ProjectApplication;
import Users.UserInterfaces.OfficerInterfaces.FlatBookingHandler;
import Users.UserInterfaces.OfficerInterfaces.OfficerAsApplicant;
import Users.UserInterfaces.QueryInterface;
import Users.UserInterfaces.StaffInterfaces.HDBStaff;

import java.util.*;

/**
 * Officer for HDB
 * Is an Applicant with the ability to assist with HDB Projects that they are interested in
 * An Officer may only assist with one project at a time and may not be an applicant for that project
 */
public class HDBOfficer extends Applicant implements HDBStaff, QueryInterface, FlatBookingHandler, OfficerAsApplicant {
    /**
     * Registration of the Officer for some project they are interested in
     */
    private OfficerRegistration officerRegistration = null;

    /**
     * The project they have been assigned to
     */
    private HDBProject assignedProject = null;

    /**
     * Creates a new Officer with standard User parameters
     * @param name This is the User's name.
     * @param nric This is the User's NRIC.
     * @param age This is the User's age
     * @param maritalStatus This is the User's marital status.
     * @param password This is the User's password
     * */
    public HDBOfficer(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }

    /**
     * Sets the assigned project of the officer to some project
     * @param project project to be assigned
     */
    public void setAssignedProject(HDBProject project) {
        this.assignedProject = project;
    }

    /**
     * Menu of options for HDB Officer
     * Calls displayMenu() of Applicant as Officer also has all Applicants capabilities
     */
    @Override
    public void displayMenu() {
        super.displayMenu();
        System.out.println("12) Register for Project");
        System.out.println("13) View Registration Status");
        System.out.println("14) View/Reply Enquiries");
        System.out.println("15) View Assigned Project Details");
        System.out.println("16) Flat Bookings");

    }

    /**
     * Calls the respective methods of the action display in displayMenu() based on some choice made by the user through getChoice()
     * @param allProjects List of allProjects
     * @param choice Selected Applicant action (Refer to displayMenu for choice mapping)
     */
    @Override
    public void handleChoice(List<HDBProject> allProjects,
                             int choice) {
        List<HDBProject> filteredProjects = new ArrayList<>(allProjects);
        if (getUserFilter() != null) {
            filteredProjects = getUserFilter().applyFilter(filteredProjects, getUserFilter());
        }
        filteredProjects.sort(Comparator.comparing(HDBProject::getName));

        if (choice == 2) {
            applyForProjectOfficer(filteredProjects);
            return;
        }
        if (choice < 12) {
            super.handleChoice(filteredProjects, choice);
            return;
        }

        switch (choice) {
            case 12:
                applyForProjectAsHDBOfficer(filteredProjects);
                break;
            case 13:
                if (officerRegistration == null) {
                    System.out.println("No application available");
                } else {
                    officerRegistration.displayApplication();
                }
                break;
            case 14:
                if (assignedProject == null) {
                    System.out.println("You do not have an assigned project yet!");
                    break;
                }
                viewEnquiries();
                break;
            case 15:
                if (assignedProject == null) {
                    System.out.println("You do not have an assigned project yet!");
                    break;
                }
                assignedProject.displayProjectStaff();
                break;
            case 16:
                if (assignedProject == null) {
                    System.out.println("You do not have an assigned project yet!");
                    break;
                }
                flatBooking();
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    /**
     * Gets the Officer's selection for action to do based on displayMenu()
     * The number of valid options is the number of Applicants capabilities + number of Officers capabilities
     * Return's -1 if the input is not a number, indicating a log-out
     * @return choice of the user
     */
    @Override
    public int getChoice() {
        Scanner sc = new Scanner(System.in);
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 1 || choice > 16) {
                    System.out.println("Invalid Selection");
                    continue;
                }
                return choice;
            } catch (InputMismatchException e) {
                sc.nextLine();
                return -1;
            }
        }
    }

    /**
     * Creates an application to be an Officer for some ongoing Project
     * @param filteredProjects projects that have bene filtered
     */
    public void applyForProjectAsHDBOfficer(List<HDBProject> filteredProjects) {
        // Check if is an existing applicant with an application
        if (this.getApplication() != null && this.getApplication().getApplicationStatus() != ApplicationStatus.UNSUCCESSFUL) {
            System.out.println("You have an ongoing application or your current application has already been approved");
            return;
        }

        // Check if already applying for another project as officer
        if (officerRegistration != null && officerRegistration.getApplicationStatus() != RegistrationStatus.UNSUCCESSFUL) {
            // TODO: Implement check for application period
            System.out.println("You have an ongoing officer application or your current officer application has already been approved");
            return;
        }

        if (assignedProject != null && assignedProject.getClosingDate().before(new Date())) {
            System.out.println("You are currently assigned to a project and the project has not closed!");
            return;
        }

        Scanner sc = new Scanner(System.in);

        displayProjects(filteredProjects);

        int choice;

        while (true) {
            try {
                choice = sc.nextInt();
                System.out.println(choice);
                if (choice >= 1 && choice <= filteredProjects.size()) {
                    //filteredProjects.get(choice - 1).displayProjectApplicant();
                    break;
                }
                System.out.println("Invalid Selection!");

            } catch (InputMismatchException e) {
                System.out.println("Invalid Selection!");
            }
        }

        HDBProject selectedProject = filteredProjects.get(choice - 1);


        officerRegistration = new OfficerRegistration(this, selectedProject);
        officerRegistration.displayApplication();
        selectedProject.addOfficerRegistration(officerRegistration);


        System.out.println("Application Successfully Created!");
    }

    /**
     * View Query's of the project the Officer is assigned to.
     */
    @Override
    public void viewEnquiries() {
        if (assignedProject == null) {
            System.out.println("No project has been assigned to you.");
            return;
        }

        List<Query> allQueries = new ArrayList<>();
        for (Query query : assignedProject.getQueries()) {
            if (query.getReply() == null) {
                allQueries.add(query);
            }
        }

        respondQuery(allQueries);
    }

    /**
     * Handles booking of Unit for successful Applicants to the Project the Officer is assigned to
     */
    @Override
    public void flatBooking() {
        List<ProjectApplication> applications = assignedProject.getAllApplicationsPendingBooking();
        if (applications.isEmpty()) {
            System.out.println("No Booking Requests have been made yet!");
            return;
        }

        // Check if any applications which are successful but have not booked a flat yet
        for (ProjectApplication application : applications) {
            // Book flat for applicant

            // 1. Update number of units in selected flat type
            // Check if sufficient units available for selected flat type
            if (application.getSelectedType().getNoOfUnitsAvailable() == 0) {
                System.out.println("Insufficient units for applicants");
                continue;
            } else {
                // Update units available
                application.getSelectedType().reserveUnit();
                // Assign unit to applicant
                application.getSelectedType().assignUnit(application.getApplicant());
            }

            // 2. Retrieve applicants BTO application with NRIC
            // TODO: Not sure if this is needed/mark-able requirement

            // 3. Update applicant's application status from successful to booked
            application.setStatus(ApplicationStatus.BOOKED);

            // 4. Update applicants profile with type of flat booked (Done in Flat assignUnit() )


            // Generate receipt
            System.out.println("Flat booked successfully!\n");
            System.out.println("Here are the booking details:");
            System.out.println("Applicant Name: " + application.getApplicant().getName());
            System.out.println("NRIC: " + application.getApplicant().getNric());
            System.out.println("Age: " + application.getApplicant().getAge());
            System.out.println("Marital Status: " + application.getApplicant().getMaritalStatus());
            System.out.println("Flat type booked: " + application.getSelectedType().getType() + "\n");
            System.out.println("Project Details:");
            assignedProject.displayProjectApplicant();
        }
    }

    @Override
    public void applyForProjectOfficer(List<HDBProject> filteredProjects) {
        // copy the list of filtered projects to prevent modification later
        List<HDBProject> applicableProjects = new ArrayList<>(filteredProjects);
        // If the officer has an assigned project we need to remove it from the list of projects they can apply for
        if (assignedProject != null) {
            applicableProjects.remove(assignedProject);
        }
        // We need to apply same rules for applying, must be visible and open for application then we can apply as per normal
        applicableProjects = getVisibleProjects(applicableProjects);
        super.applyForProject(applicableProjects);
    }
}
