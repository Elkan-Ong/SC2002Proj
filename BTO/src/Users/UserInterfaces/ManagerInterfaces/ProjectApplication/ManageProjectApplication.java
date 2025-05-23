package Users.UserInterfaces.ManagerInterfaces.ProjectApplication;

import Enums.ApplicationStatus;
import Project.HDBProject;
import Project.ProjectApplication;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Methods for Manager to handle Applications to their Project
 */
public interface ManageProjectApplication {
    /**
     * View all pending Applications
     * @param project the current Project Manager is managing
     */
    default void viewBTOApplication(HDBProject project) {
        Scanner sc = new Scanner(System.in);
        List<ProjectApplication> projectApplications = new ArrayList<>();
        // We will display only pending applications to be approved/rejected
        // Applications that have already been "answered" won't be changed, unless withdrawn by Applicant
        for (ProjectApplication application : project.getAllProjectApplications()) {
            if (application.getApplicationStatus() == ApplicationStatus.PENDING) {
                projectApplications.add(application);
            }
        }

        int choice;
        while (true) {
            if (projectApplications.isEmpty()) {
                System.out.println("No applications pending at this moment!");
                return;
            }
            System.out.println("Select Project Application for " + project.getName() + " to view: (input non-number to exit)");
            for (int i=0; i<projectApplications.size(); i++) {
                System.out.println((i+1) + ") " + projectApplications.get(i).getApplicationInfo());
            }
            try {
                choice = sc.nextInt();
                if (choice < 1 || choice > projectApplications.size()) {
                    System.out.println("Invalid Selection!");
                    continue;
                }
            } catch (InputMismatchException e) {
                break;
            }
            System.out.println("Selected Application Information:");
            ProjectApplication selectedApplication = projectApplications.get(choice-1);
            selectedApplication.displayApplication();
            manageApplication(selectedApplication, projectApplications);
        }
    }

    /**
     * Manager can approve or reject an application
     * @param application application to be rejected or approve
     */
    default void manageApplication(ProjectApplication application, List<ProjectApplication> projectApplications) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Would you like to approve or reject this application? (enter non-number to exit)");
        System.out.println("1) Approve");
        System.out.println("2) Reject");
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                if (choice < 1 || choice > 2) {
                    System.out.println("Invalid Selection!");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                choice = 0;
                break;
            }
        }
        sc.nextLine();
        if (choice == 1) {
            approveApplication(application);
            System.out.println("Application successfully approved");
        } else if (choice == 2){
            rejectApplication(application);
            System.out.println("Application successfully rejected.");
        }
        projectApplications.remove(application);
    }

    /**
     * Handles approving an application
     * @param application application to be approved
     */
    default void approveApplication(ProjectApplication application) {
        application.setStatus(ApplicationStatus.SUCCESSFUL);
    }

    /**
     * Handles rejection of application
     * @param application application to be rejected
     */
    default void rejectApplication(ProjectApplication application) {
        application.setStatus(ApplicationStatus.UNSUCCESSFUL);
    }

}
