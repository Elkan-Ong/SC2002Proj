package Users.UserInterfaces.StaffInterfaces;

import Project.HDBProject;
import Validation.BasicValidation;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Actions that all HDB Staff (Officers and Managers) have access to
 * @author Elkan Ong Han'en
 * @since 2025-4-5
 */
public interface HDBStaff extends BasicValidation {
    /**
     * Displays all the enquiries that have not been replied to
     */
    void viewEnquiries();

    /**
     * displays all the projects passed in allProjects
     * @param allProjects list of all projects
     */
    void viewProjects(List<HDBProject> allProjects);

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
}
