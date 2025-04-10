import java.text.ParseException;
import java.util.ArrayList;

import AccountHandler.Account;
import AccountHandler.AccountDisplay;
import AppInterfaces.ImportFiles;
import AppInterfaces.WriteFiles;
import Project.HDBProject;
import Users.*;
import Validation.BasicValidation;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class BTOApp implements ImportFiles, WriteFiles, BasicValidation {
    private static final AllUsers allUsers = new AllUsers();
    private static final List<HDBProject> allProjects = new ArrayList<>();
    private static final Account accountManager = new Account();

    public static void main(String[] args) throws ParseException {
        Scanner sc = new Scanner(System.in);
        try {
            ImportFiles.readAllFiles(allUsers, allProjects);
        } catch (Exception e) {
            return;
        }
        while (true) {
            System.out.println("Welcome to the BTO App!");
            System.out.println("Please login or create an account to continue (enter a non-digit to exit the app)");
            AccountDisplay.displayAccountMenu();
            int choice = 0;
            boolean exitApp = false;
            while (true) {
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                    if (choice < 1 || choice > 2) {
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
            if (choice == 2) {
                accountManager.createUser(allUsers);
                continue;
            }
            boolean exitLogin = false;
            User loggedInUser = null;
            while (true) {
                System.out.println("Enter NRIC: (To cancel login: enter -1)");
                String nric = sc.nextLine();
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
            while (true) {
                loggedInUser.displayMenu();
                int userChoice = loggedInUser.getChoice();
                if (userChoice == -1) {
                    break;
                }
                loggedInUser.handleChoice(allProjects, choice);
            }
            System.out.println("Logging out...");
            System.out.println();
        }
        System.out.println("Exiting BTO App...");
        WriteFiles.writeAllFiles(allProjects, allUsers);
    }


}
