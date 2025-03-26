package Users;

import Enums.ApplicationStatus;
import Misc.UserFilter;
import Misc.Query;
import Project.HDBProject;
import Project.ProjectApplication;
import Project.Unit;
import Users.UserInterfaces.Application;
import Users.UserInterfaces.CreateFilter;
import Users.UserInterfaces.QueryInterface;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Applicant extends User implements Application, QueryInterface, CreateFilter {
    private ProjectApplication application = null;
    private Unit bookedUnit = null;
    private UserFilter filter = null;
    // Since we don't have a query file, just take note this will reset everytime
    private ArrayList<Query> userQueries = new ArrayList<Query>();

    public Applicant(String[] values) {
        super(values[0], values[1], Integer.parseInt(values[2]), values[3], values[4]);
    }

    @Override
    public void viewProjects() {
        // get Misc.UserFilter then filter out data
        return;
    }
//

    @Override
    public void applyForProject() {
        viewProjects();
        // select project
        // set application
    }

    @Override
    public void viewApplication() {
        application.getProjectInfo();
    }

    @Override
    public void requestWithdrawal() {
        if (application == null) {
            System.out.println("You have not applied for any project!");
            return;
        }
        // TODO when implementation closer to complete, might need to check if need to change unit to available if can withdraw after booking
        application = null;
        System.out.println("Successfully withdrawn from project");
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

    // TODO for viewQuery, editQuery, and deleteQuery, do validation for choice
    // TODO can probably abstract the choice thing also, new function getQueryChoice or smth

    @Override
    public void viewQuery() {
        Scanner sc = new Scanner(System.in);
        displayQueries();
        int choice = sc.nextInt();
        System.out.println("Title:");
        System.out.println(userQueries.get(choice-1).getTitle());
        System.out.println("\nQuery:");
        System.out.println(userQueries.get(choice-1).getQuery());
        System.out.println("\nResponse:");
        System.out.println(userQueries.get(choice-1).getReply());
    }

    @Override
    public void deleteQuery() {
        Scanner sc = new Scanner(System.in);
        displayQueries();
        int choice = sc.nextInt();
        userQueries.remove(choice-1);
        System.out.println("Query removed!");
    }

    @Override
    public void editQuery() {
        Scanner sc = new Scanner(System.in);
        displayQueries();
        int choice = sc.nextInt();
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
        System.out.println("5) Create Enquiry");
        System.out.println("6) View Enquiry");
        System.out.println("7) Edit Enquiry");
        System.out.println("8) Delete Enquiry");
        System.out.println("9) Create Filter for Projects");
    }

    @Override
    public void handleChoice(int choice) {
        // implement switch case based on user input
        switch (choice) {
            case 1: viewProjects(); break;
            case 2: applyForProject(); break;
            case 3: viewApplication(); break;
            case 4: requestWithdrawal(); break;
            case 5: createQuery(); break;
            case 6: viewQuery(); break;
            case 7: editQuery(); break;
            case 8: deleteQuery(); break;
            case 9: this.filter = createFilter(); break;
            default:
                // can change to throw exception
                System.out.println("Invalid choice");
                break;
        }
    }
}
