package Project;

import Enums.ApplicationStatus;
import Users.Applicant;

public class ProjectApplication {
    private ApplicationStatus status;
    private Applicant applicant;

    public ProjectApplication(Applicant applicant) {
        this.applicant = applicant;
        this.status = ApplicationStatus.PENDING;
    }
}
