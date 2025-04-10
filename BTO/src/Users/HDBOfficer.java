package Users;

import Enums.ApplicationStatus;
import Enums.RegistrationStatus;
import Misc.OfficerRegistration;
import Misc.Query;
import Project.HDBProject;
import Project.ProjectApplication;
import Users.UserInterfaces.QueryInterface;
import Users.UserInterfaces.StaffInterfaces.HDBStaff;

import java.util.*;

public class HDBOfficer extends Applicant implements HDBStaff, QueryInterface {

    private OfficerRegistration officerRegistration = null;
    private HDBProject assignedProject = null;

    public HDBOfficer(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }

    public void setAssignedProject(HDBProject project) {
        this.assignedProject = project;
    }

    @Override
    public void viewProjects(List<HDBProject> allProjects) {
        // Display all projects
        for (int i = 0; i < allProjects.size(); i++) {
            System.out.println((i + 1) + ") " + allProjects.get(i).getName());
        }
    }

    @Override
    public void displayMenu() {
        System.out.println("What would you like to do?");
        System.out.println("1) Applicant Tasks");
        System.out.println("2) HDB Officer Tasks");

        int choice = getChoice(1, 2);

        if (choice == 1) {
            super.displayMenu();

        } else {
            System.out.println("1) Register for Project");
            System.out.println("2) View Registration Status");
            System.out.println("3) View/Reply Enquiries");
            System.out.println("4) View Project Details");
            System.out.println("5) Flat Bookings");
        }
    }


    @Override
    public void handleChoice(List<HDBProject> allProjects,
                             int choice) {
        // TODO apply user filter
        List<HDBProject> filteredProjects = allProjects;
        if (this.getFilter() != null) {
            filteredProjects = getFilter().applyFilter(filteredProjects, this.getFilter());
        }

        switch (choice) {
            case 1:
                applyForProjectAsHDBOfficer(filteredProjects);
                break;

            case 2:
                viewRegistrationStatus();
                break;

            case 3:
                if (assignedProject == null) {
                    System.out.println("You do not have an assigned project yet!");
                    break;
                }
                viewEnquiries();
                break;

            case 4:
                if (assignedProject == null) {
                    System.out.println("You do not have an assigned project yet!");
                    break;
                }
                assignedProject.displayProjectStaff();
                break;

            case 5:
                flatBooking();
                break;

            default:
                // can change to throw exception
                System.out.println("Invalid choice");
                break;
        }

    }

    @Override
    public int getChoice() {
        Scanner sc = new Scanner(System.in);
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 1 || choice > 7) {
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


    @Override
    public void displayProjects(List<HDBProject> filteredProjects) {
        HDBStaff.super.displayProjects(filteredProjects);
    }

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

        viewProjects(filteredProjects);

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


    private void viewRegistrationStatus() {
        if (officerRegistration == null) {
            System.out.println("No application available");
        } else {
            System.out.println(officerRegistration.getApplicationStatus());
        }
    }


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

    private void flatBooking() {
        List<ProjectApplication> applications = assignedProject.getAllApplicationsPendingBooking();

        // Check if any applications which are successful but have not booked a flat yet
        for (ProjectApplication application : applications) {
            // Book flat for applicant

            // 1. Update number of units in selected flat type
            // Check if sufficient units available for selected flat type
            if (application.getSelectedType().getNoOfUnitsAvailable() == 0) {
                // TODO: Throw exception and handle program flow
                System.out.println("Error! Insufficient units");
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
            System.out.println("Here are the booking details\n:");
            System.out.println("Applicant Name: " + application.getApplicant().getName());
            System.out.println("NRIC: " + application.getApplicant().getNric());
            System.out.println("Age: " + application.getApplicant().getAge());
            System.out.println("Marital Status: " + application.getApplicant().getMaritalStatus());
            System.out.println("Flat type booked: " + application.getSelectedType() + "\n");
            System.out.println("Project Details:");
            assignedProject.displayProjectApplicant();



        }
    }


}
