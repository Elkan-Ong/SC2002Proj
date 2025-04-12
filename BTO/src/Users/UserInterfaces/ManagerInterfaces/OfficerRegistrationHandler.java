package Users.UserInterfaces.ManagerInterfaces;

import Misc.OfficerRegistration;
import Project.HDBProject;

/**
 * Contains methods that are used to handle OfficerRegistrations such as viewing, and approving/rejecting the registrations
 */
public interface OfficerRegistrationHandler {
    /**
     * Displays all registrations
     * @param project project to view registrations
     */
    void viewOfficerRegistration(HDBProject project);

    /**
     * Approves the registration
     * @param registration registration to be approved
     */
    void approveRegistration(OfficerRegistration registration);

    /**
     * Rejects the registration
     * @param registration registration to be rejected
     */
    void rejectRegistration(OfficerRegistration registration);
}
