package AccountHandler.Password;

import Users.AllUsers;
import Users.User;

public class PasswordManager {
    private AllUsers allUsers;

    public PasswordManager(AllUsers allUsers) {
        this.allUsers = allUsers;
    }

    public void setAllUsers(AllUsers allUsers) {
        this.allUsers = allUsers;
    }


    public boolean changePassword(String nric, String oldPassword, String newPassword) {
        User user = getUserByNric(nric);

        if (user == null) {
            System.out.println("User not found.");
            return false;
        }

        if (!verifyOldPassword(user, oldPassword)) {
            System.out.println("Incorrect old password.");
            return false;
        }

        if (!isStrongPassword(newPassword)) {
            System.out.println("New password is too weak.");
            return false;
        }

        user.setPassword(newPassword);
        System.out.println("Password changed successfully.");
        return true;
    }

    private boolean verifyOldPassword(User user, String oldPassword) {
        return user.getPassword().equals(oldPassword);
    }

    private boolean isStrongPassword(String password) {
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

    private User getUserByNric(String nric) {
        for (User user : allUsers.getUsers()) {
            if (user.getNric().equalsIgnoreCase(nric)) {
                return user;
            }
        }
        return null;
    }
}

