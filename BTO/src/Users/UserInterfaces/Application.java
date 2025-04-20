package Users.UserInterfaces;

import Project.Flat;
import Project.HDBProject;
import Users.Applicant;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Actions relating to Application; creation, withdrawal, booking
 */
public interface Application {
    /**
     * Allows Applicant to apply for a project
     * @param filteredProject list of projects that can be applied to/the Applicant is interested in
     */
    void applyForProject(List<HDBProject> filteredProject);

    /**
     * Provides a menu for Applicant to select the type of Flat they are interested in
     * @param applicant Applicant applying for the Project
     * @return Flat selected by the Applicant
     */
    default Flat selectAvailableFlats(HDBProject project, Applicant applicant) {
        Scanner sc = new Scanner(System.in);
        List<Flat> availableFlats = new ArrayList<>();
        for (Flat flat : project.getFlatType()) {
            if (flat.getBookedUnits() < flat.getNoOfUnits()) {
                availableFlats.add(flat);
            }
        }
        if (availableFlats.isEmpty()) {
            System.out.println("No units are available");
            return null;
        }
        if (availableFlats.size() == 1 && availableFlats.getFirst().getType().equals("3-Room")) {
            if (applicant.getMaritalStatus().equals("Single")) {
                System.out.println("No flats are available for Single applicants as of now");
                return null;
            }
        }
        System.out.println("Select Flat Type:");
        for (int i=0; i < availableFlats.size(); i++) {
            System.out.println((i+1) + ") " + project.getFlatType().get(i).getType() + ": " + (project.getFlatType().get(i).getNoOfUnits() - project.getFlatType().get(i).getBookedUnits()) + " available");
        }
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                if (choice < 1 || choice > availableFlats.size()) {
                    System.out.println("Invalid Selection!");
                    continue;
                }
                if (availableFlats.get(choice-1).getType().equals("3-Room") && applicant.getMaritalStatus().equals("Single")) {
                    System.out.println("As a single applicant, you may not apply for any other flats than 2-Room");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid Selection!");
            }
            sc.nextLine();
        }
        return availableFlats.get(choice-1);
    }

    /**
     * Submit a withdrawal request to revoke Applicant's application
     */
    void requestWithdrawal();

    /**
     * Applicant can book a flat after their application is successful
     * Books an appointment with an HDB Officer to reserve a unit
     */
    void bookFlat();

}
