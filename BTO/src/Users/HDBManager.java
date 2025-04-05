package Users;

import Misc.*;
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


public class HDBManager extends User implements HDBStaff, ManagerProject, ApplicantReport, ManageProjectApplication, ManageWithdrawal{
    private HDBProject project;
    private List<HDBProject> allPastProjects = new ArrayList<>();

    public HDBManager(String[] values) {
        super(values[0], values[1], Integer.parseInt(values[2]), values[3], values[4]);
    }

    public HDBManager(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }

    public void setProject(HDBProject project) { this.project = project; }

    public void addOldProject(HDBProject project) {
        allPastProjects.add(project);
    }

    @Override
    public void viewProjects(ArrayList<HDBProject> allProjects) {

    }

    @Override
    public void displayProjects(ArrayList<HDBProject> filteredProjects) {

    }

    @Override
    public void displayProjects(List<HDBProject> filteredProjects) {
        HDBStaff.super.displayProjects(filteredProjects);
    }

    @Override
    public void viewCurrentProject() {
        if (project == null) {
            System.out.println("You are not currently managing a project!");
        } else {
            project.displayProjectApplicant();
        }
    }

    @Override
    public void deleteProject() {
        this.project = null;
    }

    @Override
    public void viewProjects(List<HDBProject> allProjects) {
        displayProjects(allProjects);
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
    public void handleChoice(List<HDBProject> allProjects,
                             List<OfficerRegistration> allRegistrations,
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
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

}









