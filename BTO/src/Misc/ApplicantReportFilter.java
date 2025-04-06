package Misc;

import Project.Flat;
import Project.HDBProject;
import Validation.BasicValidation;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Selected information that a Manager would like to filter by to create a ProjectReport
 * Filters include: project, flat types, marital status, age of applicants
 * @author Elkan Ong Han'en
 * @since 2025-4-6
 */
public class ApplicantReportFilter implements BasicValidation {
    private Scanner sc = new Scanner(System.in);
    /**
     * List of filtered Flat types
     */
    private List<String> filteredFlatTypes = new ArrayList<>();

    /**
     * List of filtered Project names
     */
    private List<String> filteredProjectNames = new ArrayList<>();

    /**
     * Maximum age of Applicants
     */
    private int maxAge;

    /**
     * Minimum age of Applicants
     */
    private int minAge;

    /**
     * List of marital statuses
     */
    private List<String> filteredMaritalStatus = new ArrayList<>();

    /**
     * Gets filtered flat types
     * @return filtered flat types
     */
    public List<String> getFilteredFlatTypes() { return filteredFlatTypes; }

    /**
     * gets filtered Project names
     * @return filtered Project names
     */
    public List<String> getFilteredProjectNames() {
        return filteredProjectNames;
    }

    /**
     * gets filtered marital statuses
     * @return filtered marital statuses
     */
    public List<String> getFilteredMaritalStatus() { return filteredMaritalStatus; }

    /**
     * Gets maximum age of Applicants
     * @return maximum age of Applicants
     */
    public int getMaxAge() { return maxAge; }

    /**
     * Gets minimum age of Applicants
     * @return minimum age of Applicants
     */
    public int getMinAge() { return minAge; }

    /**
     * Calls all the filtering methods
     * acts as a master method
     * @param projects all the Manager's projects
     */
    public void createFilter(List<HDBProject> projects) {
        filterProjectNames(projects);
        filterFlatTypes(projects);
        filterMaritalStatus();
        filterAge();
    }

    /**
     * Generic function to filter information
     * @param masterList list of options to select for filtering
     * @param addToList list of options that will be filtered
     * @param <T> Generic data type of masterList and addToList
     */
    public <T> void filterLists(List<T> masterList, List<T> addToList) {
        int choice;
        while (true) {
            if (masterList.isEmpty()) {
                System.out.println("No options left to choose from.");
                break;
            }
            System.out.println("Select option to filter: (enter non-digit character to end selection)");
            for (int i=0; i < masterList.size(); i++) {
                System.out.println((i+1) + ") " + masterList.get(i));
            }
            try {
                choice = sc.nextInt();
                if (choice < 1 || choice > masterList.size()) {
                    System.out.println("Invalid Selection!");
                    continue;
                }
                addToList.add(masterList.get(choice-1));
                masterList.remove(choice-1);
            } catch (InputMismatchException e) {
                sc.nextLine();
                break;
            }
            sc.nextLine();
        }
    }

    /**
     * Filters flat types that can be selected based on existing types within the Manager's projects
     * @param projects all the Manager's past projects
     */
    public void filterFlatTypes(List<HDBProject> projects) {
        List<String> availableFlatTypes = new ArrayList<>();
        for (HDBProject project : projects) {
            for (Flat flat : project.getFlatType()) {
                if (!availableFlatTypes.contains(flat.getType())) {
                    availableFlatTypes.add(flat.getType());
                }
            }
        }

        filterLists(availableFlatTypes, filteredFlatTypes);
    }

    /**
     * Filters project names that can be selected based on existing types within the Manager's projects
     * @param projects all the Manager's past projects
     */
    public void filterProjectNames(List<HDBProject> projects) {
        List<String> projectNames = new ArrayList<>();
        for (HDBProject project : projects) {
            if (!projectNames.contains(project.getName())) {
                projectNames.add(project.getName());
            }
        }

        filterLists(projectNames, filteredProjectNames);
    }

    /**
     * Filters marital statuses
     */
    public void filterMaritalStatus() {
        List<String> maritalStatuses = new ArrayList<>();
        maritalStatuses.add("Single");
        maritalStatuses.add("Married");

        filterLists(maritalStatuses, filteredMaritalStatus);
    }

    /**
     * Filters minimum and maximum age
     */
    public void filterAge() {
        System.out.println("Enter minimum age of Applicant:");
        this.minAge = getInt();
        System.out.println("Enter maximum age of Applicant:");
        while (true) {
            maxAge = getInt();
            if (maxAge > minAge) {
                break;
            }
            System.out.println("Maximum age must be larger than minimum age");
        }
    }
}
