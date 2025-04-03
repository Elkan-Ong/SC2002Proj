package Misc;

import Project.Flat;
import Project.HDBProject;
import Validation.BasicValidation;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ApplicantReportFilter implements BasicValidation {
    private List<String> filteredFlatTypes = new ArrayList<>();
    private List<String> filteredProjectNames = new ArrayList<>();
    private int maxAge;
    private int minAge;
    private List<String> filteredMaritalStatus = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public List<String> getFilteredFlatTypes() { return filteredFlatTypes; }

    public List<String> getFilteredProjectNames() {
        return filteredProjectNames;
    }

    public List<String> getFilteredMaritalStatus() { return filteredMaritalStatus; }

    public int getMaxAge() { return maxAge; }
    public int getMinAge() { return minAge; }

    public void createFilter(List<HDBProject> projects) {
        filterProjectNames(projects);
        filterFlatTypes(projects);
        filterMaritalStatus();
        filterAge();
    }

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

    public void filterProjectNames(List<HDBProject> projects) {
        List<String> projectNames = new ArrayList<>();
        for (HDBProject project : projects) {
            if (!projectNames.contains(project.getName())) {
                projectNames.add(project.getName());
            }
        }

        filterLists(projectNames, filteredProjectNames);
    }

    public void filterMaritalStatus() {
        List<String> maritalStatuses = new ArrayList<>();
        maritalStatuses.add("Single");
        maritalStatuses.add("Married");

        filterLists(maritalStatuses, filteredMaritalStatus);
    }

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
