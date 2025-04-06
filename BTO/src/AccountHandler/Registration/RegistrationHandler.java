package AccountHandler.Registration;

import Users.AllUsers;
import Users.User;

public class RegistrationHandler {
    private AllUsers allUsers;

    public RegistrationHandler(AllUsers allUsers) {
        this.allUsers = allUsers;
    }

    public void setAllUsers(AllUsers allUsers) {
        this.allUsers = allUsers;
    }

    // Check if a Nric is unique across all users
    public boolean isNricUnique(String nric) {
        for (User user : allUsers.getUsers()) {
            if (user.getNric().equalsIgnoreCase(nric)) {
                return false;
            }
        }
        return true;
    }

    // Add the user to the system
    public void register(User newUser) {
        allUsers.addUser(newUser);
    }
}
