package Users.UserInterfaces;

import Project.HDBProject;

import java.util.ArrayList;

public interface Application {
    void applyForProject(ArrayList<HDBProject> filteredProject);
    void viewApplication();
    void requestWithdrawal();
    void bookFlat();

}
