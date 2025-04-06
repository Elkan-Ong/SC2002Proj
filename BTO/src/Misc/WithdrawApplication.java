package Misc;

import Enums.RegistrationStatus;
import Project.ProjectApplication;
import Users.Applicant;

/**
 * Applicant can request a WithdrawalApplication when they have applied for a project but no longer wish to
 * @author Elkan Ong Han'en
 * @since 2025-4-6
 */
public class WithdrawApplication {
    /**
     * Applicant who submitted the Withdrawal
     */
    private Applicant applicant;

    /**
     * The Applicant's Application
     */
    private ProjectApplication projectApplication;

    /**
     * Status of the Withdrawal
     */
    private RegistrationStatus status;

    /**
     * Creates a new WithdrawalApplication
     *
     * @param applicant          Applicant who submitted Withdrawal request
     * @param projectApplication Application of the Applicant
     */
    public WithdrawApplication(Applicant applicant, ProjectApplication projectApplication) {
        this.applicant = applicant;
        this.projectApplication = projectApplication;
        this.status = RegistrationStatus.PENDING;
    }

    /**
     * Gets the status of the Withdrawal
     *
     * @return status of the Withdrawal
     */
    public RegistrationStatus getStatus() {
        return status;
    }

    /**
     * Changes the status of the Withdrawal
     *
     * @param status new status of Withdrawal
     */
    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    /**
     * Gets the Applicant who submitted the Withdrawal
     *
     * @return Applicant who submitted the Withdrawal
     */
    public Applicant getApplicant() {
        return applicant;
    }

    /**
     * Gets the Application of the Applicant who submitted the Withdrawal
     *
     * @return Application of the Applicant who submitted the Withdrawal
     */
    public ProjectApplication getProjectApplication() {
        return projectApplication;
    }

    /**
     * Displays information about the Withdrawal
     */
    public void displayWithdrawal() {
        System.out.println("Withdrawal Application: ");
        System.out.println("Applicant: " + applicant.getName());
        System.out.println("Project: " + projectApplication.getAppliedProject().getName());
        System.out.println("Status: " + status);
    }
}
