package Users.UserInterfaces;

import Project.HDBProject;

import java.util.ArrayList;
import java.util.List;

public interface Application {
    void applyForProject(List<HDBProject> filteredProject);
    void viewApplication();
    void requestWithdrawal();
    void bookFlat();

}
