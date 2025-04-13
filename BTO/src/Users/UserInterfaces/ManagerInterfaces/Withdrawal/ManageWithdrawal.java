package Users.UserInterfaces.ManagerInterfaces.Withdrawal;

import Enums.ApplicationStatus;
import Enums.RegistrationStatus;
import Misc.WithdrawApplication;
import Project.HDBProject;
import Project.ProjectApplication;
import Validation.BasicValidation;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Defines methods for Manager to handle withdrawal requests
 */
public interface ManageWithdrawal extends BasicValidation {
    /**
     * Displays withdrawal information and gets selection for which withdrawal the manager would like to approve/reject to
     * @param project project the Manager is handling
     */
    default void viewBTOWithdrawal(HDBProject project) {
        Scanner sc = new Scanner(System.in);
        List<WithdrawApplication> withdrawApplications = new ArrayList<>();
        // We will display only pending applications to be approved/rejected
        // Applications that have already been "answered" won't be changed, unless withdrawn by Applicant
        for (WithdrawApplication application : project.getWithdrawals()) {
            if (application.getStatus() == RegistrationStatus.PENDING) {
                withdrawApplications.add(application);
            }
        }
        int choice;
        while (true) {
            if (withdrawApplications.isEmpty()) {
                System.out.println("No more withdrawals are pending!");
                return;
            }
            System.out.println("Select Project Application for " + project.getName() + " to view: (input non-number to exit)");
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
            manageWithdrawal(selectedWithdrawal, withdrawApplications);
        }

    }

    /**
     * Manager decides if they would like to approve or reject the withdrawal request
     * @param application the withdrawal to be approved/rejected
     */
    default void manageWithdrawal(WithdrawApplication application, List<WithdrawApplication> withdrawApplications) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Would you like to approve or reject this withdrawal? (enter non-number to exit)");
        System.out.println("1) Approve");
        System.out.println("2) Reject");
        int choice = getChoice(1, 2);
        if (choice == 1) {
            approveApplication(application);
            System.out.println("Application successfully approved");
        } else if (choice == 2){
            rejectApplication(application);
            System.out.println("Application successfully rejected.");
        }
        withdrawApplications.remove(application);
    }

    /**
     * Approves a withdrawal application
     * @param application withdrawal application to be approved
     */
    default void approveApplication(WithdrawApplication application) {
        application.setStatus(RegistrationStatus.SUCCESSFUL);
        ProjectApplication projectApplication = application.getProjectApplication();
        // If a unit has been booked, we need to return that to the pool of available units
        if (projectApplication.getApplicationStatus() == ApplicationStatus.BOOKED) {
            projectApplication.getApplicant().getBookedUnit().returnUnit();
            projectApplication.getApplicant().setBookedUnit(null);
            projectApplication.getSelectedType().returnUnit();
        }
        projectApplication.setStatus(ApplicationStatus.UNSUCCESSFUL);
    }

    /**
     * Rejects a withdrawal application
     * @param application withdrawal application to be rejected
     */
    default void rejectApplication(WithdrawApplication application) {
        application.setStatus(RegistrationStatus.UNSUCCESSFUL);
        System.out.println("The following application has been rejected.");
        application.getProjectApplication().displayApplication();
    }

}
