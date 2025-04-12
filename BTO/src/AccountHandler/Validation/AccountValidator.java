package AccountHandler.Validation;

import Users.AllUsers;
import Users.User;

public interface AccountValidator {

    // Basic format check: 1 letter + 7 digits + 1 letter
    default boolean isValid(String nric) {
        if (nric == null || nric.length() != 9) {
            return false;
        }

        char prefix = Character.toUpperCase(nric.charAt(0));
        char suffix = Character.toUpperCase(nric.charAt(8));
        String digits = nric.substring(1, 8);

        // Check prefix
        if (prefix != 'S' && prefix != 'T' && prefix != 'F' && prefix != 'G') {
            return false;
        }

        // Check that middle 7 characters are all digits
        if (!digits.matches("\\d{7}")) {
            return false;
        }

        // Check suffix is an alphabet letter
        return Character.isLetter(suffix);
    }

    // Check if a Nric is unique across all users
    default boolean isNricUnique(String nric, AllUsers allUsers) {
        for (User user : allUsers.getUsers()) {
            if (user.getNric().equalsIgnoreCase(nric)) {
                return false;
            }
        }
        return true;
    }

    default boolean isStrongPassword(String password) {
        if (password.length() < 8) return false;
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true;
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
}
