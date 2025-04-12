package AccountHandler;

import AccountHandler.Password.PasswordManager;
import AccountHandler.Validation.AccountValidator;
import Users.AllUsers;
import Users.Applicant;
import Users.HDBOfficer;
import Users.HDBManager;
import Users.User;
import Validation.BasicValidation;

/**
 * Manages the handling of accounts in the BTO system
 * Users can create or login to their account
 */
public class Account implements AccountDisplay, BasicValidation, AccountValidator, PasswordManager {
    /**
     * Creates a new User in the BTO system
     * @param allUsers AllUser object which stores all the Users in the BTO System
     */
    public void createUser(AllUsers allUsers) {
        System.out.println(allUsers.getUsers());
        displayCreationMenu();
        User newUser = null;
        int choice = getChoice(1, 3);
        System.out.println("Enter Name: ");
        String name = sc.nextLine();
        String nric;
        while (true) {
            System.out.println("Enter NRIC: ");
            nric = sc.nextLine();
            nric = nric.toUpperCase();
            if (!isValid(nric) || !isNricUnique(nric, allUsers)) {
                System.out.println("Invalid NRIC!");
            } else {
                break;
            }
        }
        int age = getInt();

        String maritalStatus = "";
        System.out.println("Select martial status:");
        System.out.println("1) Single");
        System.out.println("2) Married");
        int maritalChoice = getChoice(1, 2);
        if (maritalChoice == 1) {
            maritalStatus = "Single";
        } else if (maritalChoice == 2) {
            maritalStatus = "Married";
        }

        String password;
        System.out.println("Enter your password: ");
        System.out.println("Your password should have at least one uppercase letter, lowercase letter, digit, and special character");
        do {
            password = sc.nextLine();
        } while (!isStrongPassword(password));
        newUser = switch (choice) {
            case 1 -> createApplicant(name, nric, age, maritalStatus, password);
            case 2 -> createOfficer(name, nric, age, maritalStatus, password);
            case 3 -> createManager(name, nric, age, maritalStatus, password);
            default -> null;
        };
        allUsers.addUser(newUser);
        System.out.println("You have successfully created an account!");
    }

    /**
     * Creates a new applicant
     * @param name name of user
     * @param nric nric of user
     * @param age age of user
     * @param maritalStatus marital status of user (single/married)
     * @param password password of user
     * @return Applicant object created
     */
    private Applicant createApplicant(String name, String nric, int age, String maritalStatus, String password) {
        return new Applicant(name, nric, age, maritalStatus, password);
    }

    /**
     * Creates a new Officer
     * @param name name of user
     * @param nric nric of user
     * @param age age of user
     * @param maritalStatus marital status of user (single/married)
     * @param password password of user
     * @return Officer object created
     */
    private HDBOfficer createOfficer(String name, String nric, int age, String maritalStatus, String password) {
        return new HDBOfficer(name, nric, age, maritalStatus, password);
    }

    /**
     * Creates a new Manager
     * @param name name of user
     * @param nric nric of user
     * @param age age of user
     * @param maritalStatus marital status of user (single/married)
     * @param password password of user
     * @return Manager object created
     */
    private HDBManager createManager(String name, String nric, int age, String maritalStatus, String password) {
        return new HDBManager(name, nric, age, maritalStatus, password);
    }

    /**
     * Checks the user's login information with the information in the BTO system
     * If the information matches, return the User object to log them in
     * @param nric nric of the User
     * @param password password of the User
     * @param allUsers AllUsers object containing all users in the BTO system
     * @return User logged in
     */
    public User loginAndGetUser(String nric, String password, AllUsers allUsers) {
        for (User user : allUsers.getUsers()) {
            if (user.getNric().equals(nric) && user.getPassword().equals(password)) {
                return user;
            }
            if (user.getNric().equals(nric)) {
                System.out.println("Incorrect Password!");
            }
        }
        return null;
    }
}
