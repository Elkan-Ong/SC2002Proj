package AccountHandler.Validation;

import Users.AllUsers;
import Users.User;

/**
 * Methods to validate information relating to account such as passwords and nric
 */
public interface AccountValidator {

    /**
     * Checks for valid NRIC.
     * NRIC is valid if it follows the format SXXXXXXXA
     * S depends on the millennia the user is born or some other variant, A is some random letter assigned
     * @param nric nric to check
     * @return boolean if nric is valid
     */
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

    /**
     * Checks if NRIC exists in the BTO System
     * @param nric nric to be checked
     * @param allUsers all users in the BTO system
     * @return boolean of if nric matches one in the database
     */
    default boolean isNricUnique(String nric, AllUsers allUsers) {
        for (User user : allUsers.getUsers()) {
            if (user.getNric().equalsIgnoreCase(nric)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a password is strong
     * A password is strong if it is at least 8 characters, has an upper and lower case letter, a digit, and a special character
     * @param password password to be checked
     * @return boolean of if the password is strong
     */
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
