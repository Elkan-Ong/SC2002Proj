package Misc;

import Enums.RegistrationStatus;
import Project.ProjectApplication;
import Users.Applicant;

public class WithdrawApplication {
    private Applicant applicant;
    private ProjectApplication projectApplication;
    private RegistrationStatus status;

    public WithdrawApplication(Applicant applicant, ProjectApplication projectApplication) {
        this.applicant = applicant;
        this.projectApplication = projectApplication;
        this.status = RegistrationStatus.PENDING;
    }

    public RegistrationStatus getStatus() { return status; }

    public void setStatus(RegistrationStatus status) { this.status = status; }

    public Applicant getApplicant() { return applicant; }

    public ProjectApplication getProjectApplication() { return projectApplication; }



    public void displayWithdrawal() {
        System.out.println("Withdrawal Application: ");
        System.out.println("Applicant: " + applicant.getName());
        System.out.println("Project: " + projectApplication.getAppliedProject().getName());
        System.out.println("Status: " + status);
    }

    public void approveWithdrawal() {
        // TODO need update noOfUnitsAvailable if application status is SUCCESSFUL
        this.status = RegistrationStatus.SUCCESSFUL;
        applicant.confirmWithdrawApplication();
        System.out.println("The following application has been withdrawn.");
        projectApplication.displayApplication();
    }

    public void rejectWithdrawal() {
        this.status = RegistrationStatus.UNSUCCESSFUL;
        System.out.println("The following application has been rejected.");
        projectApplication.displayApplication();
    }



}
