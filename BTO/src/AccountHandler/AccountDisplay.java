package AccountHandler;

/**
 * Displays information relating to accounts
 */
public interface AccountDisplay {
    /**
     * Displays the choices for what a user does when entering the BTO system
     */
    static void displayAccountMenu() {
        System.out.println("1) Login");
        System.out.println("2) Create Account");
        System.out.println("3) Change Password");
    }

    /**
     * Displays choices for creating an account
     * Displays the different User types
     */
    default void displayCreationMenu() {
        System.out.println("What kind of user are you?");
        System.out.println("1) Applicant");
        System.out.println("2) HDB Officer");
        System.out.println("3) HDB Manager");
    }
}
