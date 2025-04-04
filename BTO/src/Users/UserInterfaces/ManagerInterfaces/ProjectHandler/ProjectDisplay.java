package Users.UserInterfaces.ManagerInterfaces.ProjectHandler;

public interface ProjectDisplay {
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
