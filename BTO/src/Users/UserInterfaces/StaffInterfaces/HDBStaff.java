package Users.UserInterfaces.StaffInterfaces;

import Misc.Query;
import Project.HDBProject;
import Validation.BasicValidation;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Actions that all HDB Staff (Officers and Managers) have access to
 */
public interface HDBStaff extends BasicValidation {
    /**
     * Displays all the enquiries that have not been replied to
     */
    void viewEnquiries();

    /**
     * Displays all the projects and allow HDBStaff to select a project to view its full details
     * @param filteredProjects list of Projects filtered by the Staff with UserFilter
     */
    default void displayProjects(List<HDBProject> filteredProjects)  {
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
                    filteredProjects.get(choice-1).displayProjectStaff();
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
     * Staff can view and respond to queries that have not been responded to
     * @param allQueries list of queries that have yet to be responded to
     */
    default void respondQuery(List<Query> allQueries) {
        int choice;
        int respondChoice;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Select query to view: (enter non-digit to exit)");
            for (int i=0; i < allQueries.size(); i++) {
                System.out.println((i+1) + ") " + allQueries.get(i).getTitle());
            }
            try {
                choice = sc.nextInt();
                if (choice < 1 || choice > allQueries.size()) {
                    sc.nextLine();
                    System.out.println("Invalid Selection");
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                break;
            }
            Query selectedQuery = allQueries.get(choice-1);
            System.out.println("Selected Query:");
            selectedQuery.displayQuery();
            System.out.println("Would you like to respond to this query?");
            System.out.println("1) Yes");
            System.out.println("2) No");
            respondChoice = getChoice(1, 2);
            if (respondChoice == 2) {
                continue;
            }
            System.out.println("Enter reply");
            selectedQuery.setReply(sc.nextLine());
            System.out.println("Successfully replied to query!");
            selectedQuery.displayQuery();
            // whitespace
            System.out.println();
        }
    }
}
