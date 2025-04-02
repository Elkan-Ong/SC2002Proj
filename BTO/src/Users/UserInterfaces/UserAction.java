package Users.UserInterfaces;

import Misc.OfficerRegistration;
import Project.HDBProject;

import java.text.ParseException;
import java.util.List;

public interface UserAction {
    void displayMenu();
    void handleChoice(List<HDBProject> allProjects,
                      List<OfficerRegistration> allRegistrations,
                      int choice) throws ParseException;
}
