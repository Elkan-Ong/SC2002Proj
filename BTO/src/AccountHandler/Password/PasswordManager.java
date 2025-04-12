package AccountHandler.Password;

import AccountHandler.AccountDisplay;
import AccountHandler.Validation.AccountValidator;
import Users.User;

import java.util.Scanner;

public interface PasswordManager extends AccountValidator {


    default void changePassword(User user) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter New Password:");
        String newPassword;
        while (true) {
            newPassword = sc.nextLine();
            if (!isStrongPassword(newPassword)) {
                System.out.println("Password is not strong!");
                System.out.println("Your password should have at least one uppercase letter, lowercase letter, digit, and special character");
                continue;
            }
            break;
        }
        user.setPassword(newPassword);
        System.out.println("Password successfully changed!");
    }
}

