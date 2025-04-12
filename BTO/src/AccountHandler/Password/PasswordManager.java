package AccountHandler.Password;

import AccountHandler.AccountDisplay;
import AccountHandler.Validation.AccountValidator;
import Users.AllUsers;
import Users.User;

import java.util.Scanner;

public interface PasswordManager extends AccountValidator {
    default void changePassword(AllUsers allUsers) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your NRIC: (to cancel changing of password enter -1)");
        String nric;
        User userToChangePassword = null;
        while (true) {
            nric = sc.nextLine();
            if (!isValid(nric) && !nric.equals("-1")) {
                System.out.println("Invalid NRIC format!");
                continue;
            }
            if (nric.equals("-1")) {
                return;
            }
            for (User user : allUsers.getUsers()) {
                if (user.getNric().equals(nric)) {
                    userToChangePassword = user;
                    break;
                }
            }
            if (userToChangePassword == null) {
                System.out.println("NRIC not found in the system!");
                continue;
            }
            break;
        }
        System.out.println("Enter Old Password: (to cancel changing of password enter -1)");
        String password;
        while (true) {
            password = sc.nextLine();
            if (!password.equals(userToChangePassword.getPassword()) && !password.equals("-1")) {
                System.out.println("Old password is incorrect!");
                continue;
            }
            break;
        }
        if (password.equals("-1")) {
            return;
        }
        System.out.println("Enter New Password:");
        String newPassword;
        while (true) {
            newPassword = sc.nextLine();
            if (!isStrongPassword(newPassword)) {
                System.out.println("Password is not strong!");
                System.out.println("Your password should be at least 8 characters long and have at least one uppercase letter, lowercase letter, digit, and special character");
                continue;
            }
            break;
        }
        userToChangePassword.setPassword(newPassword);
        System.out.println("Password successfully changed!");
    }
}

