package Users;

import AccountHandler.Password.PasswordManager;
import Misc.Filter.UserFilter;
import Users.UserInterfaces.UserAction;

/**
 * Represents the basic details of a user in the BTO system
 * All users of this system have this class as its base class
 * Each user can have a filter to filter projects that they would be interested in
 * */
public abstract class User implements UserAction, PasswordManager {
    /**
     * Name of the user (unique)
     * */
    private final String name;
    /**
     * NRIC of user (unique)
     * */
    private final String nric;
    /**
     * Age of the user
     * */
    private final int age;
    /**
     * Marital Status of the user: Single/Married
     * */
    private final String maritalStatus;
    /**
     * Password to log in to the user's account
     * */
    private String password;
    /**
     * Filter to store the projects that the user will be interested in
     * */
    private UserFilter userFilter = null;

    /**
     * Create a new User with all attributes except userFilter
     * @param name This is the User's name.
     * @param nric This is the User's NRIC.
     * @param age This is the User's age
     * @param maritalStatus This is the User's marital status.
     * @param password This is the User's password
     * */
    public User(String name, String nric, int age, String maritalStatus, String password) {
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }

    /**
     * Gets name of user
     * @return this User's name
     * */
    public String getName() {
        return name;
    }

    /**
     * Gets nric of user
     * @return this User's nric
     * */
    public String getNric() {
        return nric;
    }

    /**
     * Gets marital status of user
     * @return this User's marital status
     * */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Gets marital status of user
     * @return this User's marital status
     * */
    public int getAge() { return age; }

    /**
     * Gets password of user
     * @return User's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password of user
     * @param password new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the filter of the User
     * @return filter of the User
     */
    public UserFilter getUserFilter() {
        return userFilter;
    }

    /**
     * Sets the filter of the User
     * @param userFilter new filter
     */
    public void setUserFilter(UserFilter userFilter) {
        this.userFilter = userFilter;
    }
}
