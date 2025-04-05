package Users;

import Misc.UserFilter;
import Project.HDBProject;
import Users.UserInterfaces.UserAction;

import java.util.ArrayList;

public abstract class User implements UserAction {
    private String name;
    private String nric;
    private int age;
    private String maritalStatus;
    private String password = "password";
    private UserFilter userFilter;

    public User(String name, String nric, int age, String maritalStatus, String password) {
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
        // create default Misc.UserFilter object when created
        // can create a method to ask user if they want to have a custom filter
        // method should also be callable later on
    }

    public String getName() {
        return name;
    }

    public String getNric() {
        return nric;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public int getAge() { return age; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public abstract void viewProjects(ArrayList<HDBProject> allProjects);

    abstract void displayProjects(ArrayList<HDBProject> filteredProjects);

}
