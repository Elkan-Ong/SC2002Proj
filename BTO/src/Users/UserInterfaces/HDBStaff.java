package Users.UserInterfaces;

import Project.HDBProject;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public interface HDBStaff {
    default void viewInquiries() {
        // Open all Queries
        // Likely need to pass in as param?
    }

    default void displayProjects(ArrayList<HDBProject> filteredProjects)  {
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
        }
    }
}
