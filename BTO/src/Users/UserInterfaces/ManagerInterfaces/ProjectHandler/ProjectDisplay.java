package Users.UserInterfaces.ManagerInterfaces.ProjectHandler;

/**
 * Menu of actions the Manager can do to edit their active project
 * @author Elkan Ong Han'en
 * @since 2025-4-6
 */
public interface ProjectDisplay {
    /**
     * Displays menu showing all the options of possible edits
     */
    default void editMenu() {
        System.out.println("What would you like to edit?");
        System.out.println("1) Project Name");
        System.out.println("2) Neighbourhood");
        System.out.println("3) Flat Type/Units");
        System.out.println("4) Opening Date");
        System.out.println("5) Closing Date");
        System.out.println("6) Officer slots");
        System.out.println("To cancel, enter a non-number character");
    }
}
