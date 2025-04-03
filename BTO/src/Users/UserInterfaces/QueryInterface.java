package Users.UserInterfaces;

import Misc.Query;
import Project.HDBProject;

import java.util.List;

public interface QueryInterface {
    // likely use default
    Query createQuery(List<HDBProject> filteredProjects);
    void viewQuery();
    void deleteQuery();
    void editQuery();
    HDBProject selectProjectForQuery(List<HDBProject> filteredProjects);
}
