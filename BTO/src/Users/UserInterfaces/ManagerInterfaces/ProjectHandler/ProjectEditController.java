package Users.UserInterfaces.ManagerInterfaces.ProjectHandler;

import Project.HDBProject;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Controller for Manager editing their active project
 */
public interface ProjectEditController extends ProjectDisplay, ProjectEditModel {
    Scanner sc = new Scanner(System.in);

    /**
     * Handles calling of respective method based on Manager's selected choice of what to edit
     * @param allProjects all projects created
     * @param project project the manager is managing
     */
    default void editProject(List<HDBProject> allProjects, HDBProject project) {
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
                editProjectName(allProjects, project);
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
