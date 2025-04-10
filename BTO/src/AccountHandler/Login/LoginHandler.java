package AccountHandler.Login;

import Users.AllUsers;
import Users.User;

public interface LoginHandler {
    default User loginAndGetUser(String nric, String password, AllUsers allUsers) {
        for (User user : allUsers.getUsers()) {
            if (user.getNric().equals(nric) &&
                user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
