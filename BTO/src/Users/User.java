package Users;

import Misc.Filter.UserFilter;
import Project.HDBProject;
import Users.UserInterfaces.UserAction;

import java.util.List;

/**
 *
 * Represents the basic details of a user in the BTO system
 * Each user can have a filter to filter projects that they would be interested in
 * @author Elkan Ong Han'en
 * @since 2025-04-05
 *
 * */
public abstract class User implements UserAction {
    /**
     * Name of the user (unique)
     * */
    private String name;
    /**
     * NRIC of user (unique)
     * */
    private String nric;
    /**
     * Age of the user
     * */
    private int age;
    /**
     * Marital Status of the user: Single/Married
     * */
    private String maritalStatus;
    /**
     * Password to log in to the user's account (by default set to "password"
     * */
    private String password = "password";
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
        // create default Misc.Filter.UserFilter object when created
        // can create a method to ask user if they want to have a custom filter
        // method should also be callable later on
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
