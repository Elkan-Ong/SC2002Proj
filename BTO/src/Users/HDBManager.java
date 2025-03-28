package Users;

import Misc.OfficerRegistration;
import Misc.Query;
import Misc.WithdrawApplication;
import Project.HDBProject;
import Project.ProjectApplication;
import Users.UserInterfaces.HDBStaff;
import Users.UserInterfaces.ManagerInterfaces.ManagerProject;

import java.text.ParseException;
import java.util.ArrayList;

public class HDBManager extends User implements HDBStaff, ManagerProject {
    private HDBProject project;

    public HDBManager(String[] values) {
        super(values[0], values[1], Integer.parseInt(values[2]), values[3], values[4]);
    }

    public HDBManager(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }

    public void test() throws ParseException {
        project = this.getProjectDetailsAndCreate(this);
        this.project.displayProject();
    }

    @Override
    public void viewCurrentProject() {
        if (project == null) {
            System.out.println("You are not currently managing a project!");
        } else {
            project.displayProject();
        }
    }

    @Override
    public void deleteProject() {
        this.project = null;
    }

    @Override
    public void viewProjects(ArrayList<HDBProject> allProjects) {
        displayProjects(allProjects, sc);
    }

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
    }

    @Override
    public void handleChoice(ArrayList<HDBProject> allProjects,
                             ArrayList<Query> allQueries,
                             ArrayList<OfficerRegistration> allRegistrations,
                             ArrayList<ProjectApplication> allProjectApplications,
                             ArrayList<WithdrawApplication> allWithdrawals, int choice) throws ParseException {
        switch (choice) {
            case 1:
                viewCurrentProject();
                break;
            case 2:
                getProjectDetailsAndCreate(this);
                break;
            case 3:
                editProject(this.project);
                break;
            case 4:
                deleteProject();
                break;
            case 5:
                viewProjects(allProjects);
                break;
            case 6:
                // Requires Officer class to be completed
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }
}
