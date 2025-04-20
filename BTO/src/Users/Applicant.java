package Users;

import Enums.ApplicationStatus;
import Enums.RegistrationStatus;
import Misc.Query;
import Misc.WithdrawApplication;
import Project.Flat;
import Project.HDBProject;
import Project.ProjectApplication;
import Project.Unit;
import Users.UserInterfaces.Application;
import Misc.Filter.CreateFilter;
import Users.UserInterfaces.QueryInterface;
import Validation.BasicValidation;

import java.util.*;

/**
 * Represent a User who is an applicant who would want to apply for a BTO project,
 * Applicants can apply for a project that is open for application if they are interested in it,
 * Applicants can only apply for projects they are eligible for
 * Applicants who are Single and over the age of 35 may apply for 2-Room flats
 * Applicants who are Married and over the age of 21 may apply for 2-Room and 3-Room flats
 * */
public class Applicant extends User implements Application, QueryInterface, CreateFilter, BasicValidation {
    /**
     * An application that the user has submitted, null if the user has no previous application
     */
    private ProjectApplication application = null;
    /**
     * The reserved unit of the Applicant when they have successfully applied for a BTO and booked a Unit through an officer.
     */
    private Unit bookedUnit = null;
    /**
     * A list of all the queries the Applicant has submitted
     */
    private final List<Query> userQueries = new ArrayList<>();
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

    /**
     * Gets the Application of the Applicant
     * @return Application of the Applicant
     */
    public ProjectApplication getApplication() {
        return application;
    }

    /**
     * Assigns application to Applicant
     * @param application application to be assigned
     */
    public void setApplication(ProjectApplication application) {
        this.application = application;
    }

    /**
     * Adds a new query to the list of user Queries
     * @param query query to be added
     */
    public void addQuery(Query query) {
        userQueries.add(query);
    }

    /**
     * Gets the Unit booked by the Applicant
     * @return Unit booked by the Applicant
     */
    public Unit getBookedUnit() { return bookedUnit; }

    /**
     * Assigns a Unit to the Applicant
     * @param unit Unit booked by the Applicant
     */
    public void setBookedUnit(Unit unit) {this.bookedUnit = unit;}

    public void setWithdrawApplication(WithdrawApplication withdrawApplication) {
        this.withdrawApplication = withdrawApplication;
    }

    /**
     * Displays all projects that have been filtered by the Applicant and is visible to them
     * A Project is visible if the Project visibility is turned on, and it is during the application period
     * @param filteredProjects list of projects the user can view
     */
    @Override
    public void displayProjects(List<HDBProject> filteredProjects) {
        Scanner sc = new Scanner(System.in);
        System.out.println("List of projects:");
        for (int i=0; i < filteredProjects.size(); i++) {
            System.out.println((i+1) + ") " + filteredProjects.get(i).getName());
        }
        int choice;
        while (true) {
            try {
                System.out.println("Select project to view: (enter non-number to exit)");
                choice = sc.nextInt();
                sc.nextLine();
                if (choice > 0 && choice <= filteredProjects.size()) {
                    HDBProject current = filteredProjects.get(choice-1);
                    current.displayProjectApplicant();
                    continue;
                }
                System.out.println("Please enter a valid project number!");
            } catch (InputMismatchException e) {
                sc.nextLine();
                break;
            }
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
        System.out.println("Select Project to apply for: (enter non-digit to exit)");
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice >= 1 && choice <= filteredProjects.size()) {
                    filteredProjects.get(choice-1).displayProjectApplicant();
                    break;
                }
                System.out.println("Invalid Selection!");

            } catch (InputMismatchException e) {
                sc.nextLine();
                return;
            }
        }
        HDBProject selectedProject = filteredProjects.get(choice - 1);
        Flat selectedType = selectAvailableFlats(selectedProject, this);
        if (selectedType == null) {
            System.out.println("Please apply for a different project");
            return;
        }
        setApplication(new ProjectApplication(this, selectedProject, selectedType));
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
     * Books an appointment with an HDB officer when the application is successful
     */
    @Override
    public void bookFlat() {
        if (bookedUnit != null) {
            System.out.println("You already have a booked unit!");
        }
        if (application.getApplicationStatus() == ApplicationStatus.PENDING) {
            System.out.println("Your application is still pending.");
        }
        if (application.getApplicationStatus() == ApplicationStatus.UNSUCCESSFUL) {
            System.out.println("Your application has been unsuccessful.");
            return;
        }
        if (application.getApplicationStatus() == ApplicationStatus.BOOKED) {
            System.out.println("You have already booked a flat.");
        }
        for (ProjectApplication projectApplication : application.getAppliedProject().getAllApplicationsPendingBooking()) {
            if (projectApplication.getApplicant().getNric().equals(application.getApplicant().getNric())) {
                System.out.println("You are currently pending a booking please wait for an Officer to assign you a unit!");
                return;
            }
        }
        application.getAppliedProject().addApplicationPendingBooking(application);
        System.out.println("Your request for a booking has been submitted!");
    }

    /**
     * Displays and gets Applicant selection for the project they would like to submit a query to
     * @param filteredProjects list of projects the Applicant is interested in
     * @return the selected project the Applicant wants to submit a query to
     */
    @Override
    public HDBProject selectProjectForQuery(List<HDBProject> filteredProjects) {
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
     */
    @Override
    public void createQuery(List<HDBProject> filteredProjects) {
        Scanner sc = new Scanner(System.in);
        if (filteredProjects.isEmpty()) {
            System.out.println("No projects to query!");
            return;
        }
        HDBProject project = selectProjectForQuery(filteredProjects);
        System.out.println("Enter query title:");
        String title = sc.nextLine();
        System.out.println("Enter your query:");
        String query = sc.nextLine();
        Query newQuery = new Query(this, project, title, query);
        this.addQuery(newQuery);
        project.addQuery(newQuery);
        System.out.println("Query Submitted!");
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
        String response = userQueries.get(choice - 1).getReply();
        System.out.println(response == null ? "No response has been made" : response);
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
            Query queryToRemove = userQueries.get(choice - 1);
            userQueries.remove(queryToRemove);
            queryToRemove.getProject().getQueries().remove(queryToRemove);
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
        System.out.println("What would you like to do? (enter non-digit to log out)");
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
     * @param projects list of all projects
     * @return List of projects the Applicant can view
     */
    public List<HDBProject> getVisibleProjects(List<HDBProject> projects) {
        List<HDBProject> result = new ArrayList<>();
        Date today = new Date();
        for (HDBProject project : projects) {
            // Ensure a project is visible, must be in an opening window
            if (project.getVisibility() &&
                    project.getOpeningDate().before(today) &&
                    project.getClosingDate().after(today) &&
                    (getMaritalStatus().equals("Married") && getAge() >= 21 || getMaritalStatus().equals("Single") && getAge() >= 35)) {
                result.add(project);
            }
        }
        return result;
    }

    /**
     * Controller to call methods of action that the Applicant has selected
     * @param allProjects List of allProjects
     * @param choice Selected Applicant action (Refer to displayMenu for choice mapping)
     */
    @Override
    public void handleChoice(List<HDBProject> allProjects,
                             int choice) {
        List<HDBProject> filteredProjects = getVisibleProjects(new ArrayList<>(allProjects));
        if (getUserFilter() != null) {
            filteredProjects = getUserFilter().applyFilter(filteredProjects, getUserFilter());
        }
        filteredProjects.sort(Comparator.comparing(HDBProject::getName));

        switch (choice) {
            case 1:
                if (filteredProjects.isEmpty()) {
                    System.out.println("No projects to display! Either no projects have been created or your filter is too strict!");
                    break;
                }
                displayProjects(filteredProjects);
                break; //done
            case 2:
                if (filteredProjects.isEmpty()) {
                    System.out.println("No projects to display! Either no projects have been created or your filter is too strict!");
                    break;
                }
                applyForProject(filteredProjects);
                break; //done
            case 3:
                if (application == null) {
                    System.out.println("You have not applied for any project!");
                    break;
                }
                application.displayApplication();
                break; //done
            case 4:
                if (application == null) {
                    System.out.println("You have not applied for any project!");
                    break;
                }
                if (withdrawApplication != null && withdrawApplication.getStatus() != RegistrationStatus.UNSUCCESSFUL) {
                    System.out.println("You have a pending withdrawal!");
                    break;
                }
                requestWithdrawal();
                break; //done
            case 5:
                if (withdrawApplication == null) {
                    System.out.println("You have not made a withdrawal request!");
                    break;
                }
                withdrawApplication.displayWithdrawal();
                break; //done
            case 6:
                if (filteredProjects.isEmpty()) {
                    System.out.println("No projects to display! Either no projects have been created or your filter is too strict!");
                    break;
                }
                createQuery(filteredProjects);
                break; //done
            case 7:
                if (userQueries.isEmpty()) {
                    System.out.println("You have not made any enquiries!");
                    break;
                }
                viewQuery();
                break; //done
            case 8:
                if (userQueries.isEmpty()) {
                    System.out.println("You have not made any enquiries!");
                    break;
                }
                editQuery();
                break; //done
            case 9:
                if (userQueries.isEmpty()) {
                    System.out.println("You have not made any enquiries!");
                    break;
                }
                deleteQuery();
                break; //done
            case 10:
                setUserFilter(createFilter(getVisibleProjects(allProjects)));
                break;
            case 11:
                if (application == null || application.getApplicationStatus() != ApplicationStatus.SUCCESSFUL) {
                    System.out.println("Invalid choice");
                }
                bookFlat();
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    /**
     * Gets Applicant's choice of what action to do based on displayMenu options
     * @return choice of the Applicant
     */
    @Override
    public int getChoice() {
        Scanner sc = new Scanner(System.in);
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 1 || choice > 11) {
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
}
