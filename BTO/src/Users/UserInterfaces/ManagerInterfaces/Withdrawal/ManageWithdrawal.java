package Users.UserInterfaces.ManagerInterfaces.Withdrawal;

import Enums.ApplicationStatus;
import Enums.RegistrationStatus;
import Misc.WithdrawApplication;
import Project.HDBProject;
import Project.ProjectApplication;
import Validation.BasicValidation;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public interface ManageWithdrawal extends BasicValidation {
    default void viewBTOWithdrawal(HDBProject project) {
        Scanner sc = new Scanner(System.in);
        ArrayList<WithdrawApplication> withdrawApplications = new ArrayList<>();
        // We will display only pending applications to be approved/rejected
        // Applications that have already been "answered" won't be changed, unless withdrawn by Applicant
        for (WithdrawApplication application : project.getWithdrawals()) {
            if (application.getStatus() == RegistrationStatus.PENDING) {
                withdrawApplications.add(application);
            }
        }
        int choice;
        while (true) {
            System.out.println("Select Project Application for " + project.getName() + " to view: (input non-number to exit");
            for (int i=0; i<withdrawApplications.size(); i++) {
                System.out.println((i+1) + ") " + withdrawApplications.get(i).getApplicant().getName() + "'s Withdrawal");
            }
            try {
                choice = sc.nextInt();
                if (choice < 1 || choice > withdrawApplications.size()) {
                    System.out.println("Invalid Selection!");
                    continue;
                }
            } catch (InputMismatchException e) {
                break;
            }
            sc.nextLine();
            System.out.println("Selected Withdrawal Information:");
            WithdrawApplication selectedWithdrawal = withdrawApplications.get(choice-1);
            selectedWithdrawal.displayWithdrawal();
            manageWithdrawal(selectedWithdrawal, project);
        }

    }

    default void manageWithdrawal(WithdrawApplication application, HDBProject project) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Would you like to approve or reject this withdrawal? (enter non-number to exit)");
        System.out.println("1) Approve");
        System.out.println("2) Reject");
        int choice = getChoice(1, 2);
        sc.nextLine();
        if (choice == 1) {
            approveApplication(application, project);
            System.out.println("Application successfully approved");
        } else if (choice == 2){
            rejectApplication(application);
            System.out.println("Application successfully rejected.");
        }
    }

    default void approveApplication(WithdrawApplication application, HDBProject project) {
        application.setStatus(RegistrationStatus.SUCCESSFUL);
        ProjectApplication projectApplication = application.getProjectApplication();
        // If a unit has been booked, we need to return that to the pool of available units
        if (projectApplication.getApplicationStatus() == ApplicationStatus.SUCCESSFUL || projectApplication.getApplicationStatus() == ApplicationStatus.BOOKED) {
            projectApplication.getSelectedType().returnUnit();
        }
        // We need to delete the application
        project.getAllProjectApplications().remove(application.getProjectApplication());
    }

    default  void rejectApplication(WithdrawApplication application) {
        application.setStatus(RegistrationStatus.UNSUCCESSFUL);
    }

}
