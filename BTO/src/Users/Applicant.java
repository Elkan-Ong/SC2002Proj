package Users;

import Enums.ApplicationStatus;
import Misc.OfficerRegistration;
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

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Applicant extends User implements Application, QueryInterface, CreateFilter {
    private ProjectApplication application = null;
    private Unit bookedUnit = null;
    private UserFilter filter = null;
    // Since we don't have a query file, just take note this will reset everytime
    private ArrayList<Query> userQueries = new ArrayList<Query>();
    private WithdrawApplication withdrawApplication;

    public Applicant(String[] values) {
        super(values[0], values[1], Integer.parseInt(values[2]), values[3], values[4]);
    }

    @Override
    public void applyForProject(ArrayList<HDBProject> filteredProjects) {
        Scanner sc = new Scanner(System.in);
        viewProjects(filteredProjects);
        System.out.println("Select Project to apply for:");
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                if (choice > 1 && choice <= filteredProjects.size()) {
                    break;
                }
                System.out.println("Invalid Selection!");

            } catch (InputMismatchException e) {
                System.out.println("Invalid Selection!");
            }
        }
        HDBProject selectedProject = filteredProjects.get(choice - 1);
        Flat selectedType = selectedProject.selectAvailableFlats();
        application = new ProjectApplication(this, selectedProject, selectedType);
        application.displayApplication();
        selectedProject.addApplication(application);
        System.out.println("Application Successfully Created!");
    }

    @Override
    public void viewApplication() {
        application.displayApplication();
    }

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

    @Override
    public Query createQuery() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter query title:");
        String title = sc.nextLine();
        System.out.println("Enter your query:");
        String query = sc.nextLine();
        Query newQuery = new Query(this, title, query);
        userQueries.add(newQuery);
        System.out.println("Query Submitted!");
        // need to return so the program can remember the query
        // we still need officers/managers to be able to view and reply
        return newQuery;
    }

    public void displayQueries() {
        System.out.println("Select Query to View:");
        for (int i = 0; i < userQueries.size(); i++) {
            System.out.println((i + 1) + ") " + userQueries.get(i).getTitle());
        }
    }


    private int validateQueryChoice() {
        Scanner sc = new Scanner(System.in);
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                if (choice > 0 && choice <= userQueries.size()) {
                    break;
                }
                System.out.println("Invalid Selection!");
            } catch (InputMismatchException e) {
                System.out.println("Invalid Selection!");
            }
        }
        return choice;
    }

    @Override
    public void viewQuery() {
        displayQueries();
        int choice = validateQueryChoice();

        System.out.println("Title:");
        System.out.println(userQueries.get(choice - 1).getTitle());
        System.out.println("\nQuery:");
        System.out.println(userQueries.get(choice - 1).getQuery());
        System.out.println("\nResponse:");
        System.out.println(userQueries.get(choice - 1).getReply());
    }

    @Override
    public void deleteQuery() {
        displayQueries();
        int choice = validateQueryChoice();
        userQueries.remove(choice - 1);
        System.out.println("Query removed!");
    }

    @Override
    public void editQuery() {
        Scanner sc = new Scanner(System.in);
        displayQueries();
        int choice = validateQueryChoice();
        System.out.println("Query:");
        System.out.println(userQueries.get(choice - 1).getQuery());
        System.out.println("\nEnter edited query:");
        String newQuery = sc.nextLine();
        userQueries.get(choice - 1).setQuery(newQuery);
        System.out.println("Query successfully edited!");
    }

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
    }

    @Override
    public void handleChoice(ArrayList<HDBProject> allProjects,
                             ArrayList<Query> allQueries,
                             ArrayList<OfficerRegistration> allRegistrations,
                             int choice) {
        // TODO apply user filter
        ArrayList<HDBProject> filteredProjects = allProjects;
        // TODO remove non visible projects

        switch (choice) {
            case 1:
                viewProjects(filteredProjects);
                break; //done
            case 2:
                applyForProject(filteredProjects);
                break; //done
            case 3:
                viewApplication();
                break; //done
            case 4:
                requestWithdrawal();
                break; //done
            case 5:
                withdrawApplication.displayWithdrawal();
                break; //done
            case 6:
                createQuery();
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
            default:
                // can change to throw exception
                System.out.println("Invalid choice");
                break;
        }
    }


    @Override
    public void viewProjects(ArrayList<HDBProject> filteredProjects) {
        Scanner sc = new Scanner(System.in);
        displayProjects(filteredProjects);
    }

    @Override
    void displayProjects(ArrayList<HDBProject> filteredProjects)  {
        Scanner sc = new Scanner(System.in);
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
                    if (current.getVisibility()) {
                        current.displayProjectApplicant();
                    }
                    continue;
                }
                System.out.println("Please enter a valid project number!");

            } catch (InputMismatchException e) {
                break;
            }
        }
    }


    public void deleteApplication() {
        this.application = null;
    }
}
