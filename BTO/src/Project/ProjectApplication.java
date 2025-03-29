package Project;

import Enums.ApplicationStatus;
import Users.Applicant;

public class ProjectApplication {
    private ApplicationStatus status;
    private Applicant applicant;
    private HDBProject appliedProject;
    private Flat selectedType;

    public ProjectApplication(Applicant applicant, HDBProject project, Flat selectedType) {
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

    public void setStatus(ApplicationStatus status) { this.status = status; }

    public Flat getSelectedType() { return selectedType; }

    public Applicant getApplicant() { return applicant; }

    public void displayApplication() {
        System.out.println("Project Application:");
        System.out.println("Project Name: " + appliedProject.getName());
        System.out.println("Applicant: " + applicant.getName());
        System.out.println("Flat Type: " + selectedType.getType());
        System.out.println("Application Status: " + status);
    }

    public String getApplicationInfo() {
        return applicant.getName() + " " + selectedType;
    }
}
