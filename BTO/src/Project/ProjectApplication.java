package Project;

import Enums.ApplicationStatus;
import Users.Applicant;

public class ProjectApplication {
    private ApplicationStatus status;
    private Applicant applicant;
    private HDBProject appliedProject;

    public ProjectApplication(Applicant applicant) {
        this.applicant = applicant;
        this.status = ApplicationStatus.PENDING;
    }

    public void getProjectInfo() {
        appliedProject.displayProject();
        // TODO display status
    }

    public ApplicationStatus getApplicationStatus() {
        return status;
    }
}
