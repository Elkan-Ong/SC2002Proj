package Misc;

import Enums.RegistrationStatus;
import Project.HDBProject;
import Users.Applicant;
import Users.HDBManager;

public class OfficerRegistration {
    private RegistrationStatus status;
    private Applicant applicant;
    private HDBProject appliedProject;
    private HDBManager approvedBy;

    public OfficerRegistration(Applicant applicant, HDBProject project) {
        this.applicant = applicant;
        this.appliedProject = project;
        this.status = RegistrationStatus.PENDING;
    }

    public RegistrationStatus getApplicationStatus() {
        return status;
    }


    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    public HDBProject getAppliedProject() {
        return appliedProject;
    }

    public void displayApplication() {
        System.out.println("Project Application:");
        System.out.println("Project Name: " + appliedProject.getName());
        System.out.println("Applicant: " + applicant.getName());
        System.out.println("Application Status: " + status);
    }
}
