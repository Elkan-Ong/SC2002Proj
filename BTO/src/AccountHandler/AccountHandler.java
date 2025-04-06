package AccountHandler;

import AccountHandler.Login.LoginHandler;
import AccountHandler.Registration.RegistrationHandler;
import AccountHandler.Password.PasswordManager;
import AccountHandler.Validation.NRICValidator;
import Users.AllUsers;
import Users.User;

public class AccountHandler {
    private AllUsers allUsers;
    private LoginHandler loginHandler;
    private RegistrationHandler registrationHandler;
    private PasswordManager passwordManager;

    public AccountHandler(AllUsers allUsers) {
        this.allUsers = allUsers;
        this.loginHandler = new LoginHandler(allUsers);
        this.registrationHandler = new RegistrationHandler(allUsers);
        this.passwordManager = new PasswordManager(allUsers);
    }

    public boolean login(String nric, String password) {
        return loginHandler.login(nric, password);
    }

    public User loginAndGetUser(String nric, String password) {
        return loginHandler.loginAndGetUser(nric, password);
    }

    public boolean registerUser(User user) {
        if (!NRICValidator.isValid(user.getNric())) {
            System.out.println("Invalid NRIC format.");
            return false;
        }

        if (!registrationHandler.isNricUnique(user.getNric())) {
            System.out.println("Nric already in use.");
            return false;
        }

        registrationHandler.register(user);
        System.out.println("User registered successfully.");
        return true;
    }

    public boolean changePassword(String nric, String oldPassword, String newPassword) {
        return passwordManager.changePassword(nric, oldPassword, newPassword);
    }


    //for global setting of allUsers list
    public void setAllUsers(AllUsers newAllUsers) {
        this.allUsers = newAllUsers;
        loginHandler.setAllUsers(newAllUsers);
        registrationHandler.setAllUsers(newAllUsers);
        passwordManager.setAllUsers(newAllUsers);
    }

    public AllUsers getAllUsers() {
        return allUsers;
    }
}
