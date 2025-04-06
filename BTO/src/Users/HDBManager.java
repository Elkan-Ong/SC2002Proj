package Users;

import Project.HDBProject;
import Users.UserInterfaces.ManagerInterfaces.ApplicantReport.ApplicantReport;
import Users.UserInterfaces.ManagerInterfaces.ProjectApplication.ManageProjectApplication;
import Users.UserInterfaces.ManagerInterfaces.Withdrawal.ManageWithdrawal;
import Users.UserInterfaces.StaffInterfaces.HDBStaff;
import Users.UserInterfaces.ManagerInterfaces.ProjectHandler.ManagerProject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents a Manager for HDB
 * A Manager can manage only 1 project at a time but has a collection of all their past projects
 * @author Elkan Ong Han'en
 * @since 2025-04-05
 */
public class HDBManager extends User implements HDBStaff, ManagerProject, ApplicantReport, ManageProjectApplication, ManageWithdrawal {
    /**
     * The current project the Manager is managing
     */
    private HDBProject project;

    /**
     * The list of all past projects (including the current project) that the manager has managed
     */
    private List<HDBProject> allPastProjects = new ArrayList<>();


    /**
     * Creates a new Manager with the basic User details
     * @param name This is the User's name.
     * @param nric This is the User's NRIC.
     * @param age This is the User's age
     * @param maritalStatus This is the User's marital status.
     * @param password This is the User's password
     */
    public HDBManager(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }

    @Override
    public void displayProjects(List<HDBProject> filteredProjects) {
        HDBStaff.super.displayProjects(filteredProjects);
    }

    /**
     * Sets the active project the manager is managing
     * @param project project the manager is managing
     */
    public void setProject(HDBProject project) { this.project = project; }

    /**
     * Adds a project to the list allPastProjects
     * @param project a project the manager has managed
     */
    public void addOldProject(HDBProject project) {
        allPastProjects.add(project);
    }

    /**
     * Displays information on the current project the Manager is managing
     */
    @Override
    public void viewCurrentProject() {
        if (project == null) {
            System.out.println("You are not currently managing a project!");
        } else {
            project.displayProjectApplicant();
        }
    }

    // TODO need to check the master/past projects lists and delete also

    /**
     * Deletes the project
     */
    @Override
    public void deleteProject(List<HDBProject> allProjects) {
        allProjects.remove(project);
        this.project = null;
    }

    /**
     *  Displays all the projects regardless of filter and who created the project
     * @param allProjects master list of all the projects
     */
    @Override
    public void viewProjects(List<HDBProject> allProjects) {
        displayProjects(allProjects);
    }

    /**
     * Displays all the actions of the Manager
     */
    @Override
    public void displayMenu() {
        System.out.println("What would you like to do?");
        System.out.println("1) View current BTO Project");
        System.out.println("2) Create BTO Project");
        System.out.println("3) Edit BTO Project");
        System.out.println("4) Delete BTO Project");
        System.out.println("5) View all BTO Projects");
        System.out.println("6) View HDB Officer Registration");
        System.out.println("7) View BTO Applications");
        System.out.println("8) View BTO Withdrawal");
        System.out.println("9) View report of applicants for current project");
        System.out.println("10) View enquiries");
        System.out.println("11) Toggle visibility of Current BTO Project");
    }

    /**
     * Controller to call methods of action that Manager has selected
     * @param allProjects master list of all the projects created
     * @param choice Selected Applicant action (Refer to displayMenu for choice mapping)
     * @throws ParseException
     */
    @Override
    public void handleChoice(List<HDBProject> allProjects,
                             int choice) throws ParseException {
        switch (choice) {
            case 1:
                viewCurrentProject();
                break;
            case 2:
                Date date = new Date();
                if (project == null || project.getClosingDate().before(date)) {
                    allPastProjects.add(getProjectDetailsAndCreate(this, allProjects));
                } else {
                    System.out.println("You are currently managing a project and the closing date has not passed.");
                }
                break;
            case 3:
                editProject(allProjects, this.project);
                break;
            case 4:
                deleteProject(allProjects);
                break;
            case 5:
                displayProjects(allProjects);
                break;
            case 6:
                // Requires Officer class to be completed
                break;
            case 7:
                viewBTOApplication(project);
                break;
            case 8:
                viewBTOWithdrawal(project);
                break;
            case 9:
                getApplicantReport(allPastProjects);
                break;
            case 10:
                viewEnquiries(allPastProjects);
                break;
            case 11:
                if (project == null) {
                    System.out.println("You are not currently managing a project!");
                    break;
                }
                project.toggleVisibility();
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }
}









