package Users;

import Enums.ApplicationStatus;
import Misc.OfficerRegistration;
import Misc.UserFilter;
import Misc.Query;
import Misc.WithdrawApplication;
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
    public ProjectApplication applyForProject(ArrayList<HDBProject> filteredProjects) {
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
        application = new ProjectApplication(this, filteredProjects.get(choice-1));
        application.displayApplication();
        System.out.println("Application Successfully Created!");
        return application;
    }

    @Override
    public void viewApplication() {
        application.displayApplication();
    }

    @Override
    public void requestWithdrawal(ArrayList<WithdrawApplication> allWithdrawals) {
        if (application == null) {
            System.out.println("You have not applied for any project!");
            return;
        }
        withdrawApplication = new WithdrawApplication(this, application);
        allWithdrawals.add(withdrawApplication);
        System.out.println("Withdraw application has been successfully submitted for the following project:");
        application.displayApplication();
    }

    @Override
    public void bookFlat() {
        if (application.getApplicationStatus() != ApplicationStatus.SUCCESSFUL) {
            System.out.println("Your application has not been approved yet!");
        }
        if (application.getApplicationStatus() == ApplicationStatus.BOOKED) {
            System.out.println("You have already booked a flat!");
        }
        // TODO booking implementation
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
        for (int i=0; i < userQueries.size(); i++) {
            System.out.println((i+1) + ") " + userQueries.get(i).getTitle());
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
        System.out.println(userQueries.get(choice-1).getTitle());
        System.out.println("\nQuery:");
        System.out.println(userQueries.get(choice-1).getQuery());
        System.out.println("\nResponse:");
        System.out.println(userQueries.get(choice-1).getReply());
    }

    @Override
    public void deleteQuery() {
        displayQueries();
        int choice = validateQueryChoice();
        userQueries.remove(choice-1);
        System.out.println("Query removed!");
    }

    @Override
    public void editQuery() {
        Scanner sc = new Scanner(System.in);
        displayQueries();
        int choice = validateQueryChoice();
        System.out.println("Query:");
        System.out.println(userQueries.get(choice-1).getQuery());
        System.out.println("\nEnter edited query:");
        String newQuery = sc.nextLine();
        userQueries.get(choice-1).setQuery(newQuery);
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
                             ArrayList<ProjectApplication> allProjectApplications,
                             ArrayList<WithdrawApplication> allWithdrawals, int choice) {
        // TODO apply user filter
        ArrayList<HDBProject> filteredProjects = allProjects;

        switch (choice) {
            case 1: viewProjects(filteredProjects); break; //done
            case 2: allProjectApplications.add(applyForProject(filteredProjects)); break; //done
            case 3: viewApplication(); break; //done
            case 4: requestWithdrawal(allWithdrawals); break; //done
            case 5: withdrawApplication.displayWithdrawal(); break; //done
            case 6: createQuery(); break; //done
            case 7: viewQuery(); break; //done
            case 8: editQuery(); break; //done
            case 9: deleteQuery(); break; //done
            case 10: this.filter = createFilter(); break;
            default:
                // can change to throw exception
                System.out.println("Invalid choice");
                break;
        }
    }


    @Override
    public void viewProjects(ArrayList<HDBProject> filteredProjects) {
        Scanner sc = new Scanner(System.in);
        displayProjects(filteredProjects, sc);
    }


}
