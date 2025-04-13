package Users.UserInterfaces.StaffInterfaces;

import Misc.Query;
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
     * Staff can view and respond to queries that have not been responded to
     * @param allQueries list of queries that have yet to be responded to
     */
    default void respondQuery(List<Query> allQueries) {
        int choice;
        int respondChoice;
        Scanner sc = new Scanner(System.in);
        while (true) {
            if (allQueries.isEmpty()) {
                System.out.println("No queries to reply to at this time!");
                return;
            }
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
            sc.nextLine();
            if (respondChoice == 2) {
                continue;
            }
            System.out.println("Enter reply");
            String reply = sc.nextLine();
            selectedQuery.setReply(reply);
            allQueries.remove(selectedQuery);
            System.out.println("Successfully replied to query!");
            selectedQuery.displayQuery();
            // whitespace
            System.out.println();
        }
    }
}
