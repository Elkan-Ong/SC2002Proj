package AccountHandler;

import AccountHandler.Login.LoginHandler;
import AccountHandler.Registration.RegistrationHandler;
import AccountHandler.Password.PasswordManager;
import AccountHandler.Validation.NRICValidator;
import Users.AllUsers;
import Users.Applicant;
import Users.HDBOfficer;
import Users.HDBManager;
import Users.User;
import Validation.BasicValidation;

import java.util.Locale;

public class Account implements AccountDisplay, BasicValidation, NRICValidator, PasswordManager, RegistrationHandler, LoginHandler {
    public void displayCreationMenu() {
        System.out.println("What kind of user are you?");
        System.out.println("1) Applicant");
        System.out.println("2) HDB Officer");
        System.out.println("3) HDB Manager");
    }

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

    private Applicant createApplicant(String name, String nric, int age, String maritalStatus, String password) {
        return new Applicant(name, nric, age, maritalStatus, password);
    }

    private HDBOfficer createOfficer(String name, String nric, int age, String maritalStatus, String password) {
        return new HDBOfficer(name, nric, age, maritalStatus, password);
    }

    private HDBManager createManager(String name, String nric, int age, String maritalStatus, String password) {
        return new HDBManager(name, nric, age, maritalStatus, password);
    }
}
