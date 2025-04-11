package Misc;

import Enums.RegistrationStatus;
import Misc.MiscInterfaces.OfficerRegistrationDisplay;
import Project.HDBProject;
import Users.Applicant;
import Users.HDBManager;
import Users.HDBOfficer;
import Users.UserInterfaces.ManagerInterfaces.OfficerRegistrationHandler;

public class OfficerRegistration implements OfficerRegistrationDisplay {
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

    @Override
    public void displayApplication() {
        System.out.println("Officer Application:");
        System.out.println("Project Name: " + appliedProject.getName());
        System.out.println("Applicant: " + applicant.getName());
        System.out.println("Application Status: " + status);
    }
}
