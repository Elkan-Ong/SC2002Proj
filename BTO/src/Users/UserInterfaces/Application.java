package Users.UserInterfaces;

import Misc.WithdrawApplication;
import Project.HDBProject;
import Project.ProjectApplication;

import java.util.ArrayList;

public interface Application {
    ProjectApplication applyForProject(ArrayList<HDBProject> filteredProject);
    void viewApplication();
    void requestWithdrawal(ArrayList<WithdrawApplication> allWithdrawals);
    void bookFlat();

}
