import java.text.ParseException;
import java.util.ArrayList;

import AccountHandler.Account;
import AccountHandler.AccountDisplay;
import AccountHandler.Validation.AccountValidator;
import AppInterfaces.ImportFiles;
import AppInterfaces.WriteFiles;
import Project.HDBProject;
import Users.*;
import Validation.BasicValidation;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for executing the program
 */
public class BTOApp implements ImportFiles, WriteFiles, BasicValidation, AccountValidator {
    /**
     * "Master" list of all users in the BTO system
     */
    private static final AllUsers allUsers = new AllUsers();
    /**
     * "Master" list of all projects in the BTO system
     */
    private static final List<HDBProject> allProjects = new ArrayList<>();
    /**
     * Acts as the main "interface" to interact with the logging in/creation of users
     */
    private static final Account accountManager = new Account();

    /**
     * Main function body
     */
    public static void main(String[] args) throws ParseException {
        Scanner sc = new Scanner(System.in);
        // Import all the files to set up our "database"
        try {
            ImportFiles.readAllFiles(allUsers, allProjects);
        } catch (Exception e) {
            // There should only be error if the files are no as provided, in that case, re-download and try again
            return;
        }
        while (true) {
            System.out.println("Welcome to the BTO App!");
            System.out.println("Please login or create an account to continue (enter a non-digit to exit the app)");
            // We start by getting the users to log in or to create an account
            AccountDisplay.displayAccountMenu();
            int choice = 0;
            boolean exitApp = false;
            while (true) {
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                    if (choice < 1 || choice > 3) {
                        System.out.println("Invalid selection");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    exitApp = true;
                    break;
                }
            }
            if (exitApp) {
                break;
            }
            // User create account
            if (choice == 2) {
                accountManager.createUser(allUsers);
                continue;
            }
            if (choice == 3) {
                accountManager.changePassword(allUsers);
                continue;
            }
            boolean exitLogin = false;
            User loggedInUser = null;
            // User login
            while (true) {
                System.out.println("Enter NRIC: (To cancel login: enter -1)");
                String nric;
                while (true) {
                    nric = sc.nextLine();
                    if (!accountManager.isValid(nric) && !nric.equals("-1")) {
                        System.out.println("Invalid NRIC format");
                        continue;
                    }
                    break;
                }

                if (nric.equals("-1")) {
                    exitLogin = true;
                    break;
                }
                System.out.println("Enter password:");
                String password = sc.nextLine();
                loggedInUser = accountManager.loginAndGetUser(nric, password, allUsers);
                if (loggedInUser == null) {
                    System.out.println("Login Unsuccessful! Please try again.");
                } else {
                    break;
                }
            }
            if (exitLogin) {
                continue;
            }
            // Displays the User's respective menu and the actions they want to do
            while (true) {
                loggedInUser.displayMenu();
                int userChoice = loggedInUser.getChoice();
                if (userChoice == -1) {
                    break;
                }
                loggedInUser.handleChoice(allProjects, userChoice);
            }
            // After user is done, we go back to logging in
            System.out.println("Logging out...");
            System.out.println();
        }
        // If the user exits the app, we will write to the files and close the program
        System.out.println("Exiting BTO App...");
        WriteFiles.writeAllFiles(allProjects, allUsers);
    }


}
