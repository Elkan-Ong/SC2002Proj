package Users.UserInterfaces;

import Misc.OfficerRegistration;
import Misc.Query;
import Project.HDBProject;

import java.text.ParseException;
import java.util.ArrayList;

public interface UserAction {
    void displayMenu();
    void handleChoice(ArrayList<HDBProject> allProjects,
                      ArrayList<Query> allQueries,
                      ArrayList<OfficerRegistration> allRegistrations,
                      int choice) throws ParseException;
}
