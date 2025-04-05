package AccountHandler.Login;

import Users.AllUsers;
import Users.User;

public class LoginHandler {
    private AllUsers allUsers;

    public LoginHandler(AllUsers allUsers) {
        this.allUsers = allUsers;
    }

    // Setter to be used when someone create account
    public void setAllUsers(AllUsers allUsers) {
        this.allUsers = allUsers;
    }

    public boolean login(String nric, String password) {
        for (User user : allUsers.getUsers()) {
            if (user.getNric().equals(nric) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public User loginAndGetUser(String nric, String password) {
        for (User user : allUsers.getUsers()) {
            if (user.getNric().equals(nric) &&
                user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
