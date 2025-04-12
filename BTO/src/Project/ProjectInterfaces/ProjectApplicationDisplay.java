package Project.ProjectInterfaces;

/**
 * Methods to display information about a ProjectApplication
 */
public interface ProjectApplicationDisplay {
    /**
     * Displays information of the Application
     */
    void displayApplication();

    /**
     * Displays information of the Project, to be used by HDB Staff
     */
    String getApplicationInfo();
}
