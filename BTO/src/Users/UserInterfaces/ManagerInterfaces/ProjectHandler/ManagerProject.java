package Users.UserInterfaces.ManagerInterfaces.ProjectHandler;

import Project.HDBProject;

import java.util.List;

/**
 * Methods for the Manager relating to handling the project including creation and editing
 */
public interface ManagerProject extends ProjectDisplay, ProjectCreation, ProjectEditModel, ProjectEditController {
    /**
     * Deletes the active project of the manager and removes it from the list of all projects
     * @param allProjects list of all projects created
     */
    void deleteProject(List<HDBProject> allProjects);

    /**
     * Displays information of the active Project the Manager is managing
     */
    void viewCurrentProject();
}
