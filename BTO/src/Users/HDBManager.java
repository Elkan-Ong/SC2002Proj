package Users;

import Enums.RegistrationStatus;
import Misc.*;
import Misc.Filter.CreateFilter;
import Project.HDBProject;
import Users.UserInterfaces.ManagerInterfaces.ApplicantReport.ApplicantReport;
import Users.UserInterfaces.ManagerInterfaces.OfficerRegistrationHandler;
import Users.UserInterfaces.ManagerInterfaces.ProjectApplication.ManageProjectApplication;
import Users.UserInterfaces.ManagerInterfaces.Withdrawal.ManageWithdrawal;
import Users.UserInterfaces.StaffInterfaces.HDBStaff;
import Users.UserInterfaces.ManagerInterfaces.ProjectHandler.ManagerProject;

import java.util.*;

/**
 * Represents a Manager for HDB
 * A Manager can manage only 1 project at a time but has a collection of all their past projects
 */
public class HDBManager extends User implements HDBStaff, ManagerProject, ApplicantReport, ManageProjectApplication, ManageWithdrawal, OfficerRegistrationHandler, CreateFilter {
    /**
     * The current project the Manager is managing
     */
    private HDBProject project;

    /**
     * The list of all past projects (including the current project) that the manager has managed
     */
    private final List<HDBProject> allPastProjects = new ArrayList<>();


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

    /**
     * Displays all the filtered projects of the Manager
     * @param filteredProjects projects that have been filtered
     */
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
            project.displayProjectStaff();
        }
    }


    /**
     * Deletes the project
     */
    @Override
    public void deleteProject(List<HDBProject> allProjects) {
        allProjects.remove(project);
        allPastProjects.remove(project);
        this.project = null;
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
        System.out.println("12) Create Filter For Projects");
        System.out.println("13) Change password");
    }

    /**
     * Controller to call methods of action that Manager has selected
     * @param allProjects master list of all the projects created
     * @param choice Selected Applicant action (Refer to displayMenu for choice mapping)
     */
    @Override
    public void handleChoice(List<HDBProject> allProjects,
                             int choice) {
        List<HDBProject> filteredProjects = new ArrayList<>(allProjects);
        if (getUserFilter() != null) {
            filteredProjects = getUserFilter().applyFilter(filteredProjects, getUserFilter());
        }
        switch (choice) {
            case 1:
                if (project == null) {
                    System.out.println("You are not currently managing a project!");
                    break;
                }
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
                if (project == null) {
                    System.out.println("You are not currently managing a project!");
                    break;
                }
                editProject(allProjects, this.project);
                break;
            case 4:
                if (project == null) {
                    System.out.println("You are not currently managing a project!");
                    break;
                }
                deleteProject(allProjects);
                break;
            case 5:
                displayProjects(filteredProjects);
                break;
            case 6:
                if (project == null) {
                    System.out.println("You are not currently managing a project!");
                    break;
                }
                viewOfficerRegistration(project);
                break;
            case 7:
                if (project == null) {
                    System.out.println("You are not currently managing a project!");
                    break;
                }
                viewBTOApplication(project);
                break;
            case 8:
                if (project == null) {
                    System.out.println("You are not currently managing a project!");
                    break;
                }
                viewBTOWithdrawal(project);
                break;
            case 9:
                getApplicantReport(allPastProjects);
                break;
            case 10:
                if (project == null) {
                    System.out.println("You are not currently managing a project!");
                    break;
                }
                viewEnquiries();
                break;
            case 11:
                if (project == null) {
                    System.out.println("You are not currently managing a project!");
                    break;
                }
                project.toggleVisibility();
                break;
            case 12:
                setUserFilter(createFilter(allProjects));
            case 13:
                changePassword(this);
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    /**
     * Gets the Manager's selection for action to do based on displayMenu()
     * Return's -1 if the input is not a number, indicating a log-out
     * @return choice of the user
     */
    @Override
    public int getChoice() {
        Scanner sc = new Scanner(System.in);
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                sc.nextLine();
                // choice to be edited if menu is expanded/shrunk
                if (choice < 1 || choice > 13) {
                    System.out.println("Invalid Selection");
                    continue;
                }
                return choice;
            } catch (InputMismatchException e) {
                sc.nextLine();
                return -1;
            }
        }
    }

    /**
     * View all pending registrations of Officers that want to join the Project
     * @param project Project the manager is managing
     */
    @Override
    public void viewOfficerRegistration(HDBProject project) {
        List<OfficerRegistration> applications = new ArrayList<>();
        for (OfficerRegistration registration : project.getOfficerApplications()) {
            if (registration.getApplicationStatus() == RegistrationStatus.PENDING) {
                applications.add(registration);
            }
        }
        if (applications.isEmpty()) {
            System.out.println("No pending applications!");
            return;
        }
        while (true) {
            System.out.println("Select which Officer Registration you would like to view: (enter non-digit to exit)");
            for (int i=0; i < applications.size(); i++) {
                System.out.println((i+1) + ") " + applications.get(i).getApplicant().getName() + "'s Application");
            }
            int choice;
            Scanner sc = new Scanner(System.in);
            while (true) {
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                    if (choice < 1 || choice > applications.size()) {
                        System.out.println("Invalid Input");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    sc.nextLine();
                    return;
                }
            }
            OfficerRegistration selectedRegistration = applications.get(choice-1);
            selectedRegistration.displayApplication();
            System.out.println("Would you like to approve or reject this application? (enter non-digit to exit)");
            System.out.println("1) Accept");
            System.out.println("2) Reject");
            while (true) {
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                    if (choice < 1 || choice > 2) {
                        System.out.println("Invalid Input");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    sc.nextLine();
                    return;
                }
            }
            if (choice == 1) {
                if (project.getAvailableOfficerSlots() > project.getAssignedOfficers().size()) {
                    approveRegistration(selectedRegistration);
                } else {
                    System.out.println("All officer slots have been filled for your project!");
                    return;
                }
            } else {
                rejectRegistration(selectedRegistration);
            }
            applications.remove(selectedRegistration);
        }
    }

    /**
     * Approves the registration of an Officer to a Project
     * Assigns the Project to the Officer and adds them to the list of assignedOfficers
     * @param registration the OfficerRegistration to be approved
     */
    @Override
    public void approveRegistration(OfficerRegistration registration) {
        registration.setStatus(RegistrationStatus.SUCCESSFUL);
        project.assignOfficer(registration.getApplicant());
        registration.getApplicant().setAssignedProject(project);
        System.out.println("Registration successfully Approved!");
    }

    /**
     * Rejects the registration of an Officer to a Project
     * @param registration the OfficerRegistration to be rejected
     */
    @Override
    public void rejectRegistration(OfficerRegistration registration) {
        registration.setStatus(RegistrationStatus.UNSUCCESSFUL);
        System.out.println("Registration successfully Rejected!");
    }

    /**
     * Displays all unanswered Query's of all the projects the manager has created
     * Manager may select a Query to respond to
     */
    @Override
    public void viewEnquiries() {
        if (allPastProjects.isEmpty()) {
            System.out.println("No projects have been made.");
            return;
        }

        List<Query> allQueries = new ArrayList<>();
        for (HDBProject project : allPastProjects) {
            for (Query query : project.getQueries()) {
                if (query.getReply() == null) {
                    allQueries.add(query);
                }
            }
        }
        respondQuery(allQueries);
    }
}









