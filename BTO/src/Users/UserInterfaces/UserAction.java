package Users.UserInterfaces;

import Project.HDBProject;

import java.text.ParseException;
import java.util.List;

/**
 * Defines the actions that are common between all users
 */
public interface UserAction {
    /**
     * Display the actions the User is able to do
     */
    void displayMenu();

    /**
     * Controller to call methods of action that User has selected
     * @param allProjects master list of all the projects created
     * @param choice User's input of the action they have selected to do in reference to the list displayMenu produces
     * @throws ParseException
     */
    void handleChoice(List<HDBProject> allProjects,
                      int choice) throws ParseException;
    /**
     * All users will be able to see projects
     * The projects that can be viewed are determined by the different User types
     * Refer to specific User types for more information
     * */
    void viewProjects(List<HDBProject> allProjects);
}
