package AccountHandler.Validation;

public interface NRICValidator {

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
}
