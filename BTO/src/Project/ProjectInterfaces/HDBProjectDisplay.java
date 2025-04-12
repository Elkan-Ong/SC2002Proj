package Project.ProjectInterfaces;

/**
 * Methods to display project information
 * Different User types will see different information
 */
public interface HDBProjectDisplay {
    /**
     * Displays information of the Project to be used by Applicants
     */
    void displayProjectApplicant();

    /**
     * Displays information of the Project to be used by HDB Staff
     */
    void displayProjectStaff();
}
