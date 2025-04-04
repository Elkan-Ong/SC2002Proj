package Users.UserInterfaces.ManagerInterfaces.ProjectHandler;

public interface ManagerProject extends ProjectDisplay, ProjectCreation, ProjectEditModel, ProjectEditController {
    void deleteProject();
    void viewCurrentProject();
}
