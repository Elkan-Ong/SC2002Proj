package Users.UserInterfaces.OfficerInterfaces;

import Project.HDBProject;

import java.util.List;

/**
 * Contains Methods for when the Officer is applying to a Project as an Applicant
 */
public interface OfficerAsApplicant {
    /**
     * Lets an Officer apply for a Project as an Applicant
     * Additional checks to ensure they do not apply for a project they are registered/registering to
     * @param filteredProjects List of Projects the Officer has filtered
     */
    void applyForProjectOfficer(List<HDBProject> filteredProjects);
}
