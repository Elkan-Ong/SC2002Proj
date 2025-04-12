package Users.UserInterfaces;

import Misc.Query;
import Project.HDBProject;

import java.util.List;

/**
 * Defines the methods required for interacting and creating Query objects
 */
public interface QueryInterface {
    /**
     * Creates a new Query for some specific project
     * @param filteredProjects a list of projects
     * @return Created Query object based on title, query, date created
     */
    Query createQuery(List<HDBProject> filteredProjects);

    /**
     * Display all the queries that have been submitted
     */
    void displayQueries();

    /**
     * Display a specific Query along with its details
     */
    void viewQuery();

    /**
     * Deletes a Query
     */
    void deleteQuery();

    /**
     * Edit the query of the Query object
     */
    void editQuery();

    /**
     * Chooses a project that the Query is to be submitted to
     * @param filteredProjects list of projects that have been filtered by the User
     * @return HDBProject that has been selected that User wants to enquire about
     */
    HDBProject selectProjectForQuery(List<HDBProject> filteredProjects);
}
