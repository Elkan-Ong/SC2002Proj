package Users;

import Enums.ApplicationStatus;
import Misc.UserFilter;
import Misc.Query;
import Misc.WithdrawApplication;
import Project.Flat;
import Project.HDBProject;
import Project.ProjectApplication;
import Project.Unit;
import Users.UserInterfaces.Application;
import Users.UserInterfaces.CreateFilter;
import Users.UserInterfaces.QueryInterface;
import Validation.BasicValidation;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Represent a User who is an applicant who would want to apply for a BTO project
 * @author Elkan Ong Han'en
 * @since 2025-04-05
 *
 * */
public class Applicant extends User implements Application, QueryInterface, CreateFilter, BasicValidation {
    /**
     * An application that the user has submitted, null if user has no previous application
     */
    private ProjectApplication application = null;
    /**
     * The reserved unit of the Applicant when they have successfully applied for a BTO and booked a Unit through an officer.
     */
    private Unit bookedUnit = null;
    /**
     * User's filter to filter projects they would like to view
     */
    private UserFilter filter = null;
    /**
     * A list of all the queries the Applicant has submitted
     */
    private List<Query> userQueries = new ArrayList<>();
    /**
     * A withdrawal application if the user wishes to no longer be considered for a project
     */
    private WithdrawApplication withdrawApplication = null;

    /**
     * Creates a new Applicant with basic User details
     * @param name This is the User's name.
     * @param nric This is the User's NRIC.
     * @param age This is the User's age
     * @param maritalStatus This is the User's marital status.
     * @param password This is the User's password
     */
    public Applicant(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }

    public ProjectApplication getApplication() {
        return application;
    }

    @Override
    void displayProjects(List<HDBProject> filteredProjects) {
        Scanner sc = new Scanner(System.in);
        List<HDBProject> displayableProjects = new ArrayList<>();
        for (HDBProject project : filteredProjects) {
            if (project.getVisibility()) {
                displayableProjects.add(project);
            }
        }
        if (filteredProjects.isEmpty()) {
            System.out.println("No projects have been created.");
            return;
        }
        System.out.println("List of projects:");
        for (int i=0; i < filteredProjects.size(); i++) {
            System.out.println((i+1) + ") " + filteredProjects.get(i).getName());
        }
        int choice;
        while (true) {
            try {
                System.out.println("Select project to view: (enter non-number to exit)");
                choice = sc.nextInt();
                if (choice > 0 && choice <= filteredProjects.size()) {
                    HDBProject current = filteredProjects.get(choice-1);
                    current.displayProjectApplicant();
                    continue;
                }
                System.out.println("Please enter a valid project number!");
            } catch (InputMismatchException e) {
                break;
            }
            sc.nextLine();
        }
    }

    /**
     * Lets Applicant apply for a project they are interested in
     * @param filteredProjects the list of projects the Applicant is interested in
     */
    @Override
    public void applyForProject(List<HDBProject> filteredProjects) {
        if (application != null && application.getApplicationStatus() != ApplicationStatus.UNSUCCESSFUL) {
            System.out.println("You have an ongoing application or your current application has already been approved");
            return;
        }
        if (getMaritalStatus().equals("Single") && getAge() < 35 || getMaritalStatus().equals("Married") && getAge() < 21) {
            System.out.println("You are ineligible for any flats");
            System.out.println("You must be at least 35 to apply for a 2-Room or Married and above 21 to apply for 2-Room or 3-Room");
            return;
        }
        Scanner sc = new Scanner(System.in);
        displayProjects(filteredProjects);
        System.out.println("Select Project to apply for:");
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                if (choice >= 1 && choice <= filteredProjects.size()) {
                    filteredProjects.get(choice-1).displayProjectApplicant();
                    break;
                }
                System.out.println("Invalid Selection!");

            } catch (InputMismatchException e) {
                System.out.println("Invalid Selection!");
            }
        }
        sc.nextLine();
        HDBProject selectedProject = filteredProjects.get(choice - 1);
        Flat selectedType = selectAvailableFlats(selectedProject, this);
        if (selectedType == null) {
            System.out.println("Please apply for a different project");
            return;
        }
        application = new ProjectApplication(this, selectedProject, selectedType);
        application.displayApplication();
        selectedProject.addApplication(application);
        System.out.println("Application Successfully Created!");
    }

    /**
     * Creates a withdrawal application for the project the Applicant has applied for
     */
    @Override
    public void requestWithdrawal() {
        if (application == null) {
            System.out.println("You have not applied for any project!");
            return;
        }
        withdrawApplication = new WithdrawApplication(this, application);
        application.getAppliedProject().addWithdrawal(withdrawApplication);
        System.out.println("Withdraw application has been successfully submitted for the following project:");
        application.displayApplication();
    }

    /**
     * Books an appointment with a HDB officer when the application is successful
     */
    @Override
    public void bookFlat() {
        if (application.getApplicationStatus() == ApplicationStatus.PENDING) {
            System.out.println("Your application is still pending.");
        }
        if (application.getApplicationStatus() == ApplicationStatus.UNSUCCESSFUL) {
            System.out.println("Your application has been unsuccessful.");
            return;
        }
        if (application.getApplicationStatus() == ApplicationStatus.BOOKED) {
            System.out.println("You have already booked a flat.");
            return;
        }
        // TODO send to HDBOfficer booking implementation
    }

    /**
     * Displays and gets Applicant selection for the project they would like to submit a query to
     * @param filteredProjects list of projects the Applicant is interested in
     * @return the selected project the Applicant wants to submit a query to
     */
    @Override
    public HDBProject selectProjectForQuery(List<HDBProject> filteredProjects) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select which project you would like to submit a query for:");
        for (int i=0; i < filteredProjects.size(); i++) {
            System.out.println((i+1) + ") " + filteredProjects.get(i).getName());
        }
        int choice = getChoice(1, filteredProjects.size());
        return filteredProjects.get(choice-1);
    }

    /**
     * Creates a Query on a specific project the Applicant would like to inquire about
     * @param filteredProjects list of projects the Applicant is interested in
     * @return Query that was created
     */
    @Override
    public Query createQuery(List<HDBProject> filteredProjects) {
        Scanner sc = new Scanner(System.in);
        HDBProject project = selectProjectForQuery(filteredProjects);
        System.out.println("Enter query title:");
        String title = sc.nextLine();
        System.out.println("Enter your query:");
        String query = sc.nextLine();
        Query newQuery = new Query(this, project, title, query);
        userQueries.add(newQuery);
        project.addQuery(newQuery);
        System.out.println("Query Submitted!");
        // need to return so the program can remember the query
        // we still need officers/managers to be able to view and reply
        return newQuery;
    }

    /**
     * Displays all the Applicant's submitted queries
     */
    @Override
    public void displayQueries() {
        System.out.println("Select Query to View:");
        for (int i = 0; i < userQueries.size(); i++) {
            System.out.println((i + 1) + ") " + userQueries.get(i).getTitle());
        }
    }

    /**
     * Displays a specific query that the Applicant has submitted
     * Able to view the response of the query
     */
    @Override
    public void viewQuery() {
        displayQueries();
        int choice = getChoice(1, userQueries.size());

        System.out.println("Title:");
        System.out.println(userQueries.get(choice - 1).getTitle());
        System.out.println("\nQuery:");
        System.out.println(userQueries.get(choice - 1).getQuery());
        System.out.println("\nResponse:");
        System.out.println(userQueries.get(choice - 1).getReply());
    }

    /**
     * Deletes a query that has been submitted by the Applicant
     * Cannot be deleted if the Query has been replied to
     */
    @Override
    public void deleteQuery() {
        displayQueries();
        int choice = getChoice(1, userQueries.size());
        if (userQueries.get(choice-1).getReply() == null) {
            userQueries.remove(choice - 1);
            System.out.println("Query removed!");
        } else {
            System.out.println("Your query has been answered. It can no longer be deleted!");
        }

    }

    /**
     * Allows Applicant to edit the details of a query they have submitted
     * Cannot be edited if the query has already been replied to
     * User will need to resubmit a new Query if they would like to add more details/specifics, but it has been replied to
     */
    @Override
    public void editQuery() {
        Scanner sc = new Scanner(System.in);
        displayQueries();
        int choice = getChoice(1, userQueries.size());
        if (userQueries.get(choice-1).getReply() == null) {
            System.out.println("Query:");
            System.out.println(userQueries.get(choice - 1).getQuery());
            System.out.println("\nEnter edited query:");
            String newQuery = sc.nextLine();
            userQueries.get(choice - 1).setQuery(newQuery);
            System.out.println("Query successfully edited!");
        } else {
            System.out.println("Your query has been answered. It can no longer be deleted!");
        }
    }

    /**
     * Displays all the actions of the Applicant
     */
    @Override
    public void displayMenu() {
        System.out.println("What would you like to do?");
        System.out.println("1) View available projects");
        System.out.println("2) Apply for a project");
        System.out.println("3) View project application");
        System.out.println("4) Request Application Withdrawal");
        System.out.println("5) View Application Withdrawal Status");
        System.out.println("6) Create Enquiry");
        System.out.println("7) View Enquiry");
        System.out.println("8) Edit Enquiry");
        System.out.println("9) Delete Enquiry");
        System.out.println("10) Create Filter for Projects");
        if (application != null && application.getApplicationStatus() == ApplicationStatus.SUCCESSFUL) {
            System.out.println("11) Book Flat");
        }
    }

    /**
     * Filters projects that are visible
     * @param projects
     * @return List of projects the Applicant can view
     */
    public List<HDBProject> getVisibleProjects(List<HDBProject> projects) {
        List<HDBProject> result = new ArrayList<>();
        for (HDBProject project : projects) {
            if (project.getVisibility()) {
                result.add(project);
            }
        }
        return result;
    }

    /**
     * Controller to call methods of action that Applicant has selected
     * @param allProjects List of allProjects
     * @param choice Selected Applicant action (Refer to displayMenu for choice mapping)
     */
    @Override
    public void handleChoice(List<HDBProject> allProjects,
                             int choice) {
        // TODO apply user filter
        // TODO viewProjects should be redundant; can jump to displayProjects, when filterProjects is applied for Users, it should also remove invisible projects as well as those they are not eligible to apply for
        List<HDBProject> filteredAllProjects = allProjects;
        List<HDBProject> filteredProjects = getVisibleProjects(filteredAllProjects);

        switch (choice) {
            case 1:
                displayProjects(filteredProjects);
                break; //done
            case 2:
                applyForProject(filteredProjects);
                break; //done
            case 3:
                if (application == null) {
                    System.out.println("You have not applied for any projects!");
                    break;
                }
                application.displayApplication();
                break; //done
            case 4:
                requestWithdrawal();
                break; //done
            case 5:
                withdrawApplication.displayWithdrawal();
                break; //done
            case 6:
                createQuery(filteredProjects);
                break; //done
            case 7:
                viewQuery();
                break; //done
            case 8:
                editQuery();
                break; //done
            case 9:
                deleteQuery();
                break; //done
            case 10:
                this.filter = createFilter();
                break;

            case 11:
                application.getAppliedProject().addApplicationPendingBooking(application);
                break;

            default:
                // can change to throw exception
                System.out.println("Invalid choice");
                break;
        }
    }

    /**
     * Changes ApplicationStatus of application to UNSUCCESSFUL when the user confirms their withdrawal
     */
    public void confirmWithdrawApplication() {
        this.application.setStatus(ApplicationStatus.UNSUCCESSFUL);
    }


    public void setBookedUnit(Unit unit) {this.bookedUnit = unit;}

}
