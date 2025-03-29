package Users;

import Enums.ApplicationStatus;
import Enums.RegistrationStatus;
import Misc.ApplicantReportFilter;
import Misc.OfficerRegistration;
import Misc.Query;
import Misc.WithdrawApplication;
import Project.HDBProject;
import Project.ProjectApplication;
import Users.UserInterfaces.HDBStaff;
import Users.UserInterfaces.ManagerInterfaces.ManagerProject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HDBManager extends User implements HDBStaff, ManagerProject {
    private HDBProject project;
    private ArrayList<HDBProject> allPastProjects = new ArrayList<>();

    public HDBManager(String[] values) {
        super(values[0], values[1], Integer.parseInt(values[2]), values[3], values[4]);
    }

    public HDBManager(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }

    public void addOldProject(HDBProject project) {
        allPastProjects.add(project);
    }

    @Override
    public void displayProjects(ArrayList<HDBProject> filteredProjects) {
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
    public void viewProjects(ArrayList<HDBProject> allProjects) {
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
    public void handleChoice(ArrayList<HDBProject> allProjects,
                             ArrayList<Query> allQueries,
                             ArrayList<OfficerRegistration> allRegistrations,
                             int choice) throws ParseException {
        switch (choice) {
            case 1:
                viewCurrentProject();
                break;
            case 2:
                Date date = new Date();
                if (project == null || project.getClosingDate().before(date)) {
                    allPastProjects.add(getProjectDetailsAndCreate(this));
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
                viewBTOApplication();
                break;
            case 8:
                viewBTOWithdrawal();
                break;
            case 9:
                getApplicantReport();
                break;
            case 10:
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    public ApplicantReportFilter createReportFilter() {
        ApplicantReportFilter filter = new ApplicantReportFilter();
        filter.createFilter(allPastProjects);
        return filter;
    }

    public void filterApplicants(ArrayList<ProjectApplication> filteredApplications, ApplicantReportFilter applicantFilter) {
        boolean exit;
        for (ProjectApplication application : project.getAllProjectApplications()) {
            exit = true;
            if (!applicantFilter.getFilteredFlatTypes().isEmpty()) {
                for (String flatType : applicantFilter.getFilteredFlatTypes()) {
                    if (application.getSelectedType().getType().equals(flatType)) {
                        exit = false;
                        break;
                    }
                }
                if (exit) {
                    continue;
                }
            }

            exit = true;
            if (!applicantFilter.getFilteredProjectNames().isEmpty()) {
                for (String projectName : applicantFilter.getFilteredProjectNames()) {
                    if (application.getProjectName().equals(projectName)) {
                        exit = false;
                        break;
                    }
                }
                if (exit) {
                    continue;
                }
            }


            exit = true;
            if (!applicantFilter.getFilteredMaritalStatus().isEmpty()) {
                for (String maritalStatus : applicantFilter.getFilteredMaritalStatus()) {
                    if (application.getApplicant().getMaritalStatus().equals(maritalStatus)) {
                        exit = false;
                        break;
                    }
                }
                if (exit) {
                    continue;
                }
            }


            int applicantAge = application.getApplicant().getAge();
            if (applicantAge > applicantFilter.getMaxAge() || applicantAge < applicantFilter.getMinAge()) {
                continue;
            }

            filteredApplications.add(application);
        }
    }

    public void getApplicantReport(){
        ArrayList<ProjectApplication> filteredApplications = new ArrayList<>();
        ApplicantReportFilter applicantFilter = createReportFilter();
        filterApplicants(filteredApplications, applicantFilter);

        // might want to create a new class to store project filtered information
        // need a new method or edit old to store no. of applicants, for each flat type how many single/married applicants, min max age of applicants for a project
        // probably edit >:c
    }


    public void viewBTOApplication() {
        Scanner sc = new Scanner(System.in);
        ArrayList<ProjectApplication> projectApplications = new ArrayList<>();
        // We will display only pending applications to be approved/rejected
        // Applications that have already been "answered" won't be changed, unless withdrawn by Applicant
        for (ProjectApplication application : project.getAllProjectApplications()) {
            if (application.getApplicationStatus() == ApplicationStatus.PENDING) {
                projectApplications.add(application);
            }
        }

        int choice;
        while (true) {
            System.out.println("Select Project Application for " + project.getName() + " to view: (input non-number to exit");
            for (int i=0; i<projectApplications.size(); i++) {
                System.out.println((i+1) + ") " + projectApplications.get(i).getApplicationInfo());
            }
            try {
                choice = sc.nextInt();
                if (choice < 1 || choice > projectApplications.size()) {
                    System.out.println("Invalid Selection!");
                    continue;
                }
            } catch (InputMismatchException e) {
                break;
            }
            System.out.println("Selected Application Information:");
            ProjectApplication selectedApplication = projectApplications.get(choice-1);
            selectedApplication.displayApplication();
            manageApplication(selectedApplication);
        }
    }

    public void manageApplication(ProjectApplication application) {
        System.out.println("Would you like to approve or reject this application? (enter non-number to exit)");
        System.out.println("1) Approve");
        System.out.println("2) Reject");
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                if (choice < 1 || choice > 2) {
                    System.out.println("Invalid Selection!");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                choice = 0;
                break;
            }
        }
        if (choice == 1) {
            approveApplication(application);
            System.out.println("Application successfully approved");
        } else if (choice == 2){
            rejectApplication(application);
            System.out.println("Application successfully rejected.");
        }
    }

    public void approveApplication(ProjectApplication application) {
        application.setStatus(ApplicationStatus.SUCCESSFUL);
        application.getSelectedType().reserveUnit();
    }

    public void rejectApplication(ProjectApplication application) {
        application.setStatus(ApplicationStatus.UNSUCCESSFUL);
    }

    public void viewBTOWithdrawal() {
        Scanner sc = new Scanner(System.in);
        ArrayList<WithdrawApplication> withdrawApplications = new ArrayList<>();
        // We will display only pending applications to be approved/rejected
        // Applications that have already been "answered" won't be changed, unless withdrawn by Applicant
        for (WithdrawApplication application : project.getWithdrawals()) {
            if (application.getStatus() == RegistrationStatus.PENDING) {
                withdrawApplications.add(application);
            }
        }
        int choice;
        while (true) {
            System.out.println("Select Project Application for " + project.getName() + " to view: (input non-number to exit");
            for (int i=0; i<withdrawApplications.size(); i++) {
                System.out.println((i+1) + ") " + withdrawApplications.get(i).getApplicant().getName() + "'s Withdrawal");
            }
            try {
                choice = sc.nextInt();
                if (choice < 1 || choice > withdrawApplications.size()) {
                    System.out.println("Invalid Selection!");
                    continue;
                }
            } catch (InputMismatchException e) {
                break;
            }
            System.out.println("Selected Withdrawal Information:");
            WithdrawApplication selectedWithdrawal = withdrawApplications.get(choice-1);
            selectedWithdrawal.displayWithdrawal();
            manageWithdrawal(selectedWithdrawal);
        }

    }

    public void manageWithdrawal(WithdrawApplication application) {
        System.out.println("Would you like to approve or reject this withdrawal? (enter non-number to exit)");
        System.out.println("1) Approve");
        System.out.println("2) Reject");
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                if (choice < 1 || choice > 2) {
                    System.out.println("Invalid Selection!");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                choice = 0;
                break;
            }
        }
        if (choice == 1) {
            approveApplication(application);
            System.out.println("Application successfully approved");
        } else if (choice == 2){
            rejectApplication(application);
            System.out.println("Application successfully rejected.");
        }
    }

    public void approveApplication(WithdrawApplication application) {
        application.setStatus(RegistrationStatus.SUCCESSFUL);
        ProjectApplication projectApplication = application.getProjectApplication();
        // If a unit has been booked, we need to return that to the pool of available units
        if (projectApplication.getApplicationStatus() == ApplicationStatus.SUCCESSFUL || projectApplication.getApplicationStatus() == ApplicationStatus.BOOKED) {
            projectApplication.getSelectedType().returnUnit();
        }
        // We need to delete the application
        project.getAllProjectApplications().remove(application.getProjectApplication());
    }

    public void rejectApplication(WithdrawApplication application) {
        application.setStatus(RegistrationStatus.UNSUCCESSFUL);
    }

}









