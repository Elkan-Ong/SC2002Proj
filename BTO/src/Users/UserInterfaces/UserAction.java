package Users.UserInterfaces;

import Misc.OfficerRegistration;
import Misc.Query;
import Misc.WithdrawApplication;
import Project.HDBProject;
import Project.ProjectApplication;

import java.text.ParseException;
import java.util.ArrayList;

public interface UserAction {
    void displayMenu();
    void handleChoice(ArrayList<HDBProject> allProjects,
                      ArrayList<Query> allQueries,
                      ArrayList<OfficerRegistration> allRegistrations,
                      ArrayList<ProjectApplication> allProjectApplications,
                      ArrayList<WithdrawApplication> allWithdrawals,
                      int choice) throws ParseException;
}
