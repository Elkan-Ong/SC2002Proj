package Users.UserInterfaces.ManagerInterfaces;

import Misc.OfficerRegistration;
import Project.HDBProject;

public interface OfficerRegistrationHandler {
    void viewOfficerRegistration(HDBProject project);
    void approveRegistration(OfficerRegistration registration);
    void rejectRegistration(OfficerRegistration registration);
}
