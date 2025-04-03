package Users;

import Enums.ApplicationStatus;
import Misc.OfficerRegistration;
import Misc.UserFilter;
import Misc.Query;
import Misc.WithdrawApplication;
import Project.Flat;
import Project.HDBProject;
import Project.ProjectApplication;
import Project.Unit;
import Users.UserInterfaces.Application;
import Users.UserInterfaces.CreateFilter;
import Users.UserInterfaces.QueryInterface;
import Validation.BasicValidation;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Applicant extends User implements Application, QueryInterface, CreateFilter, BasicValidation {
    private ProjectApplication application = null;
    private Unit bookedUnit = null;
    private UserFilter filter = null;
    // Since we don't have a query file, just take note this will reset everytime
    private ArrayList<Query> userQueries = new ArrayList<Query>();
    private WithdrawApplication withdrawApplication;

    public Applicant(String[] values) {
        super(values[0], values[1], Integer.parseInt(values[2]), values[3], values[4]);
    }

    public Applicant(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }

    @Override
    public void applyForProject(List<HDBProject> filteredProjects) {
        if (application != null && application.getApplicationStatus() != ApplicationStatus.UNSUCCESSFUL) {
            System.out.println("You have an ongoing application or your current application has already been approved");
            return;
        }
        Scanner sc = new Scanner(System.in);
        viewProjects(filteredProjects);
        System.out.println("Select Project to apply for:");
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                if (choice >= 1 && choice <= filteredProjects.size()) {
                    filteredProjects.get(choice-1).displayProjectApplicant();
                    break;
                }
                System.out.println("Invalid Selection!");

            } catch (InputMismatchException e) {
                System.out.println("Invalid Selection!");
            }
        }
        sc.nextLine();
        HDBProject selectedProject = filteredProjects.get(choice - 1);
        Flat selectedType = selectedProject.selectAvailableFlats();
        if (selectedType == null) {
            System.out.println("Please apply for a different project");
            return;
        }
        application = new ProjectApplication(this, selectedProject, selectedType);
        application.displayApplication();
        selectedProject.addApplication(application);
        System.out.println("Application Successfully Created!");
    }

    @Override
    public void viewApplication() {
        application.displayApplication();
    }

    @Override
    public void requestWithdrawal() {
        if (application == null) {
            System.out.println("You have not applied for any project!");
            return;
        }
        withdrawApplication = new WithdrawApplication(this, application);
        application.getAppliedProject().addWithdrawal(withdrawApplication);
        System.out.println("Withdraw application has been successfully submitted for the following project:");
        application.displayApplication();
    }

    @Override
    public void bookFlat() {
        if (application.getApplicationStatus() == ApplicationStatus.PENDING) {
            System.out.println("Your application is still pending.");
        }
        if (application.getApplicationStatus() == ApplicationStatus.UNSUCCESSFUL) {
            System.out.println("Your application has been unsuccessful.");
            return;
        }
        if (application.getApplicationStatus() == ApplicationStatus.BOOKED) {
            System.out.println("You have already booked a flat.");
            return;
        }
        // TODO send to HDBOfficer booking implementation
    }

    @Override
    public HDBProject selectProjectForQuery(List<HDBProject> filteredProjects) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select which project you would like to submit a query for:");
        for (int i=0; i < filteredProjects.size(); i++) {
            System.out.println((i+1) + ") " + filteredProjects.get(i).getName());
        }
        int choice = getChoice(1, filteredProjects.size());
        return filteredProjects.get(choice-1);
    }

    @Override
    public Query createQuery(List<HDBProject> filteredProjects) {
        Scanner sc = new Scanner(System.in);
        HDBProject project = selectProjectForQuery(filteredProjects);
        System.out.println("Enter query title:");
        String title = sc.nextLine();
        System.out.println("Enter your query:");
        String query = sc.nextLine();
        Query newQuery = new Query(this, project, title, query);
        userQueries.add(newQuery);
        project.addQuery(newQuery);
        System.out.println("Query Submitted!");
        // need to return so the program can remember the query
        // we still need officers/managers to be able to view and reply
        return newQuery;
    }

    public void displayQueries() {
        System.out.println("Select Query to View:");
        for (int i = 0; i < userQueries.size(); i++) {
            System.out.println((i + 1) + ") " + userQueries.get(i).getTitle());
        }
    }


    private int validateQueryChoice() {
        Scanner sc = new Scanner(System.in);
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                if (choice > 0 && choice <= userQueries.size()) {
                    break;
                }
                System.out.println("Invalid Selection!");
            } catch (InputMismatchException e) {
                System.out.println("Invalid Selection!");
            }
        }
        sc.nextLine();
        return choice;
    }

    @Override
    public void viewQuery() {
        displayQueries();
        int choice = validateQueryChoice();

        System.out.println("Title:");
        System.out.println(userQueries.get(choice - 1).getTitle());
        System.out.println("\nQuery:");
        System.out.println(userQueries.get(choice - 1).getQuery());
        System.out.println("\nResponse:");
        System.out.println(userQueries.get(choice - 1).getReply());
    }

    @Override
    public void deleteQuery() {
        displayQueries();
        int choice = validateQueryChoice();
        if (userQueries.get(choice-1).getReply() == null) {
            userQueries.remove(choice - 1);
            System.out.println("Query removed!");
        } else {
            System.out.println("Your query has been answered. It can no longer be deleted!");
        }

    }

    @Override
    public void editQuery() {
        Scanner sc = new Scanner(System.in);
        displayQueries();
        int choice = validateQueryChoice();
        if (userQueries.get(choice-1).getReply() == null) {
            System.out.println("Query:");
            System.out.println(userQueries.get(choice - 1).getQuery());
            System.out.println("\nEnter edited query:");
            String newQuery = sc.nextLine();
            userQueries.get(choice - 1).setQuery(newQuery);
            System.out.println("Query successfully edited!");
        } else {
            System.out.println("Your query has been answered. It can no longer be deleted!");
        }
    }

    @Override
    public void displayMenu() {
        System.out.println("What would you like to do?");
        System.out.println("1) View available projects");
        System.out.println("2) Apply for a project");
        System.out.println("3) View project application");
        System.out.println("4) Request Application Withdrawal");
        System.out.println("5) View Application Withdrawal Status");
        System.out.println("6) Create Enquiry");
        System.out.println("7) View Enquiry");
        System.out.println("8) Edit Enquiry");
        System.out.println("9) Delete Enquiry");
        System.out.println("10) Create Filter for Projects");
    }

    public List<HDBProject> getVisibleProjects(List<HDBProject> projects) {
        List<HDBProject> result = new ArrayList<>();
        for (HDBProject project : projects) {
            if (project.getVisibility()) {
                result.add(project);
            }
        }
        return result;
    }

    @Override
    public void handleChoice(List<HDBProject> allProjects,
                             List<OfficerRegistration> allRegistrations,
                             int choice) {
        // TODO apply user filter
        // TODO viewProjects should be redundant; can jump to displayProjects, when filterProjects is applied for Users, it should also remove invisible projects as well as those they are not eligible to apply for
        List<HDBProject> filteredAllProjects = allProjects;
        List<HDBProject> filteredProjects = getVisibleProjects(filteredAllProjects);

        switch (choice) {
            case 1:
                viewProjects(filteredProjects);
                break; //done
            case 2:
                applyForProject(filteredProjects);
                break; //done
            case 3:
                viewApplication();
                break; //done
            case 4:
                requestWithdrawal();
                break; //done
            case 5:
                withdrawApplication.displayWithdrawal();
                break; //done
            case 6:
                createQuery(filteredProjects);
                break; //done
            case 7:
                viewQuery();
                break; //done
            case 8:
                editQuery();
                break; //done
            case 9:
                deleteQuery();
                break; //done
            case 10:
                this.filter = createFilter();
                break;
            default:
                // can change to throw exception
                System.out.println("Invalid choice");
                break;
        }
    }

    // TODO delete when filter done
    @Override
    public void viewProjects(List<HDBProject> filteredProjects) {
        Scanner sc = new Scanner(System.in);
        ArrayList<HDBProject> displayableProjects = new ArrayList<>();
        for (HDBProject project : filteredProjects) {
            if (project.getVisibility()) {
                displayableProjects.add(project);
            }
        }
        displayProjects(displayableProjects);
    }

    @Override
    void displayProjects(List<HDBProject> filteredProjects)  {
        Scanner sc = new Scanner(System.in);
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
                    HDBProject current = filteredProjects.get(choice-1);
                    current.displayProjectApplicant();
                    continue;
                }
                System.out.println("Please enter a valid project number!");
            } catch (InputMismatchException e) {
                break;
            }
            sc.nextLine();
        }
    }


    public void confirmWithdrawApplication() {
        this.application.setStatus(ApplicationStatus.UNSUCCESSFUL);
    }
}
