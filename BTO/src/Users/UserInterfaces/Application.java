package Users.UserInterfaces;

import Project.HDBProject;

import java.util.List;

/**
 * Actions relating to Application; creation, withdrawal, booking
 */
public interface Application {
    /**
     * Allows Applicant to apply for a project
     * @param filteredProject list of projects that can be applied to/the Applicant is interested in
     */
    void applyForProject(List<HDBProject> filteredProject);

    /**
     * Submit a withdrawal request to revoke Applicant's application
     */
    void requestWithdrawal();

    /**
     * Applicant can book a flat after their application is successful
     * Books an appointment with an HDB Officer to reserve a unit
     */
    void bookFlat();

}
