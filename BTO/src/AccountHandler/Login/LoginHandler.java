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

    public User login(String username, String password) {
        Optional<User> user = allUsers.findUser(username);
    }

}
