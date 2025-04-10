package Misc;

import Enums.RegistrationStatus;
import Project.HDBProject;
import Users.Applicant;
import Users.HDBManager;
import Users.HDBOfficer;

public class OfficerRegistration {
    private RegistrationStatus status;
    private final HDBOfficer applicant;
    private final HDBProject appliedProject;

    public OfficerRegistration(HDBOfficer applicant, HDBProject project) {
        this.applicant = applicant;
        this.appliedProject = project;
        this.status = RegistrationStatus.PENDING;
    }

    public HDBOfficer getApplicant() { return applicant; }

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
        System.out.println("Officer Application:");
        System.out.println("Project Name: " + appliedProject.getName());
        System.out.println("Applicant: " + applicant.getName());
        System.out.println("Application Status: " + status);
    }
}
