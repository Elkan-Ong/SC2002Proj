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
     * Controller to call methods of action that the User has selected
     * @param allProjects master list of all the projects created
     * @param choice User's input of the action they have selected to do in reference to the list displayMenu produces
     */
    void handleChoice(List<HDBProject> allProjects,
                      int choice) throws ParseException;

    /**
     * Prompt User for a choice that is valid based on the number of capabilities the User has
     * @return choice selection of the User based on capabilities
     */
    int getChoice();

    /**
     * Display projects visible to the User
     * @param filteredProjects projects that have been filtered by the User using UserFilter
     */
    void displayProjects(List<HDBProject> filteredProjects);
}
