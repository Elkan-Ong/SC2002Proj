package Project;

import Enums.ApplicationStatus;
import Users.Applicant;

public class ProjectApplication {
    private ApplicationStatus status;
    private Applicant applicant;
    private HDBProject appliedProject;
    private String selectedType;

    public ProjectApplication(Applicant applicant, HDBProject project, String selectedType) {
        this.applicant = applicant;
        this.appliedProject = project;
        this.selectedType = selectedType;
        this.status = ApplicationStatus.PENDING;
    }

    public String getProjectName() { return appliedProject.getName(); }

    public ApplicationStatus getApplicationStatus() {
        return status;
    }

    public HDBProject getAppliedProject() { return appliedProject; }

    public void displayApplication() {
        System.out.println("Project Application:");
        System.out.println("Project Name: " + appliedProject.getName());
        System.out.println("Applicant: " + applicant.getName());
        System.out.println("Flat Type: " + selectedType);
        System.out.println("Application Status: " + status);
    }
}
