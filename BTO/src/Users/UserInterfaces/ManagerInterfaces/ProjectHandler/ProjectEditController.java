package Users.UserInterfaces.ManagerInterfaces.ProjectHandler;

import Project.HDBProject;

import java.util.InputMismatchException;
import java.util.Scanner;

public interface ProjectEditController extends ProjectDisplay, ProjectEditModel {
    Scanner sc = new Scanner(System.in);
    default void editProject(HDBProject project) {
        editMenu();
        int choice;
        try {
            choice = sc.nextInt();
            while (choice < 1 || choice > 6) {
                System.out.println("Invalid selection!");
                System.out.println("Enter selection:");
                choice = sc.nextInt();
            }
            sc.nextLine(); // clear buffer in case
        } catch (InputMismatchException e) {
            return;
        }
        switch (choice) {
            case 1:
                editProjectName(project);
                break;
            case 2:
                editNeighbourhood(project);
                break;
            case 3:
                editFlat(project);
                break;
            case 4:
                editOpeningDate(project);
                break;
            case 5:
                editClosingDate(project);
                break;
            case 6:
                editOfficerSlots(project);
                break;
        }
        project.displayProjectStaff();
    }
}
