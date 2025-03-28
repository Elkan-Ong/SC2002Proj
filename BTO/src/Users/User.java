package Users;

import Misc.OfficerRegistration;
import Misc.Query;
import Misc.UserFilter;
import Project.HDBProject;
import Users.UserInterfaces.UserAction;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

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

    public void changePassword(String old_password, String new_password) {
        // TODO
        if (this.password.equals(old_password)) {
            this.password = new_password;
        }
        // else throw exception for incorrect password?
    }

    public abstract void viewProjects(ArrayList<HDBProject> allProjects);

    static void displayProjects(ArrayList<HDBProject> filteredProjects, Scanner sc) {
        if (filteredProjects.isEmpty()) {
            System.out.println("No projects have been created.");
            return;
        }
        System.out.println("List of projects:");
        for (int i=0; i < filteredProjects.size(); i++) {
            System.out.println((i+1) + ") " + filteredProjects.get(i).getName());
        }
        int choice;
        while (true) {
            try {
                System.out.println("Select project to view: (enter non-number to exit)");
                choice = sc.nextInt();
                if (choice > 0 && choice <= filteredProjects.size()) {
                    filteredProjects.get(choice-1).displayProject();
                    continue;
                }
                System.out.println("Please enter a valid project number!");

            } catch (InputMismatchException e) {
                break;
            }
        }
    }

}
