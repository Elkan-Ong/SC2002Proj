package AccountHandler.Registration;

import Users.AllUsers;
import Users.User;

public interface RegistrationHandler {
//    private AllUsers allUsers;
//
//    public RegistrationHandler(AllUsers allUsers) {
//        this.allUsers = allUsers;
//    }
//
//    public void setAllUsers(AllUsers allUsers) {
//        this.allUsers = allUsers;
//    }

    // Check if a Nric is unique across all users
    default boolean isNricUnique(String nric, AllUsers allUsers) {
        for (User user : allUsers.getUsers()) {
            if (user.getNric().equalsIgnoreCase(nric)) {
                return false;
            }
        }
        return true;
    }

//    // Add the user to the system
//    public void register(User newUser) {
//        allUsers.addUser(newUser);
//    }
}
