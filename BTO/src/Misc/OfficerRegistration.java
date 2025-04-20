package Misc;

import Enums.RegistrationStatus;
import Misc.MiscInterfaces.OfficerRegistrationDisplay;
import Project.HDBProject;
import Users.HDBOfficer;

/**
 * A Registration made by an Officer when they want to be assigned to help with a Project
 * They will indicate their interest to a Project,
 * and the Manager will be able to view and approve/reject their registration
 */
public class OfficerRegistration implements OfficerRegistrationDisplay {
    /**
     * Status of the registration
     * Pending/Successful/Unsuccessful
     */
    private RegistrationStatus status;

    /**
     * The Officer registering for the project
     */
    private final HDBOfficer applicant;

    /**
     * The project the Officer is registering for
     */
    private final HDBProject appliedProject;

    /**
     * Creates an OfficerRegistration based on the Officer registering, and the Project they are registering for
     * Default status is set to Pending
     * @param applicant Officer registering for the Project
     * @param project Project the Officer is registering for
     */
    public OfficerRegistration(HDBOfficer applicant, HDBProject project) {
        this.applicant = applicant;
        this.appliedProject = project;
        this.status = RegistrationStatus.PENDING;
    }

    /**
     * Gets the Officer who made the Registration
     * @return the Officer who made the Registration
     */
    public HDBOfficer getApplicant() { return applicant; }

    /**
     * Gets the status of the application
     * @return status of the application
     */
    public RegistrationStatus getApplicationStatus() {
        return status;
    }

    /**
     * Updates the status of the application
     * @param status the status of the application
     */
    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    /**
     * Gets the Project that the Officer registered for
     * @return the Project that the Officer registered for
     */
    public HDBProject getAppliedProject() {
        return appliedProject;
    }

    /**
     * Displays information about the Registration made
     */
    @Override
    public void displayApplication() {
        System.out.println("Officer Application:");
        System.out.println("Project Name: " + appliedProject.getName());
        System.out.println("Applicant: " + applicant.getName());
        System.out.println("Application Status: " + status);
    }
}
