package Users.UserInterfaces.ManagerInterfaces.ProjectHandler;

import Project.AvailableFlatTypes;
import Project.Flat;
import Project.HDBProject;
import Project.Unit;
import Validation.BasicValidation;

import java.util.Date;
import java.util.List;

/**
 * Model for when Manager wants to edit active Project
 * @author Elkan Ong Han'en
 * @since 2025-4-6
 */
public interface ProjectEditModel extends BasicValidation, AvailableFlatTypes {
    /**
     * Selector for editing the flat type, flat price, or number of units for each flat
     * @param project project to be edited
     */
    default void editFlat(HDBProject project) {
        for (int i=0; i < project.getFlatType().size(); i++) {
            System.out.println((i+1) + ") " + project.getFlatType().get(i).getType());
        }
        System.out.println("Which flat would you like to edit?");
        int choice = getChoice(1, project.getFlatType().size());
        Flat selectedFlat = project.getFlatType().get(choice-1);
        System.out.println("Do you want to edit flat type or the number of units?");
        System.out.println("1) Flat Type");
        System.out.println("2) Units");
        choice = getChoice(1, 2);
        if (choice == 1) {
            editFlatType(project, selectedFlat);
        } else {
            editUnits(selectedFlat);
        }
    }

    /**
     * Edits the number of Units in the flat
     * Minimum number of units is determined by the highest number of units booked at the time of editing
     * e.g. if the 15th unit has been booked, minimally must maintain 15 units
     * @param flat flat to have units edited
     */
    default void editUnits(Flat flat) {
        System.out.println("Enter no. of units: ");
        int minUnits = 1;
        List<Unit> units = flat.getUnits();
        for (int i=units.size()-1; i >= 0; i--) {
            if (units.get(i).getBooked() == true) {
                minUnits = i+1;
                break;
            }
        }
        System.out.println("Note: " + minUnits + " th unit has been booked, you may not reduce the number of units below this number");
        int newUnits = getChoice(minUnits, Integer.MAX_VALUE);
        if (newUnits > flat.getNoOfUnits()) {
            flat.addUnits(newUnits - flat.getNoOfUnits());
            flat.setNoOfUnits(newUnits);
        } else {
            flat.removeUnits(flat.getNoOfUnits() - newUnits);
            flat.setNoOfUnits(newUnits);
        }
        System.out.println("Successfully updated units for " + flat.getType());
    }

    /**
     * Edits the Flat's type (e.g. from 2-Room to 3-Room)
     * new Flat type cannot be same as the other Flat type
     * @param flat flat type to be changed
     */
    default void editFlatType(HDBProject project, Flat flat) {
        Flat otherFlat = null;
        for (Flat existingFlat : project.getFlatType()) {
            if (!existingFlat.getType().equals(flat.getType())) {
                otherFlat = existingFlat;
                break;
            }
        }
        System.out.println("Select flat type to change to:");
        for (int i=0; i < availableTypes.length; i++) {
            System.out.println((i+1) + ") " + availableTypes[i]);
        }
        int choice;
        while (true) {
            choice = getChoice(1, availableTypes.length);
            if (availableTypes[choice-1].equals(otherFlat.getType())) {
                System.out.println("Selected flat type cannot be the same as another existing flat type!");
                continue;
            }
            break;
        }

        flat.setType(availableTypes[choice-1]);
    }

    /**
     * Edits the neighbourhood the Project will be built in
     * @param project project to be edited
     */
    default void editNeighbourhood(HDBProject project) {
        System.out.println("Enter new neighbourhood name:");
        String newNeighbourhood = sc.nextLine();
        project.setNeighbourhood(newNeighbourhood);
        System.out.println("Successfully updated neighbourhood.");
    }

    /**
     * Edits the number of officer slots in the Project
     * @param project project to be edited
     */
    default void editOfficerSlots(HDBProject project) {
        System.out.println("Enter new number of officer slots:");
        if (!project.getAssignedOfficers().isEmpty()) {
            System.out.println("Note: No. of slots cannot be less than " + project.getAssignedOfficers().size() + " without unassigning officers first.");
        }
        int newSlots = getChoice(project.getAssignedOfficers().size(), Integer.MAX_VALUE);
        project.setAvailableOfficerSlots(newSlots);
    }

    /**
     * Edits the name of the Project
     * Project name cannot be same as an existing project
     * @param project project to be edited
     */
    default void editProjectName(List<HDBProject> allProjects, HDBProject project) {
        System.out.println("Enter new project name:");
        String name = sc.nextLine();
        boolean exit;
        while (true) {
            exit = true;
            for (HDBProject existingProject : allProjects) {
                if (name.equals(existingProject.getName())) {
                    System.out.println("New project name cannot be the same as another existing project's name!");
                    System.out.println("Please enter a new name:");
                    name = sc.nextLine();
                    exit = false;
                    break;
                }
            }
            if (exit) {
                break;
            }
        }
        project.setName(name);
        System.out.println("Project name successfully updated!");
    }

    /**
     * Edits the opening date of the project
     * @param project project to be edited
     */
    default void editOpeningDate(HDBProject project) {
        System.out.println("Enter new opening date:");
        Date openingDate;
        while (true) {
            openingDate = validateDate();
            if (openingDate.before(project.getClosingDate())) {
                project.setOpeningDate(openingDate);
                break;
            } else {
                System.out.println("Opening date cannot be after closing date!");
            }
        }
    }

    /**
     * Edits the closing date of the project
     * @param project project to be edited
     */
    default void editClosingDate(HDBProject project) {
        System.out.println("Enter new closing date:");
        Date closingDate;
        while (true) {
            closingDate = validateDate();
            if (closingDate.after(project.getOpeningDate())) {
                project.setClosingDate(closingDate);
                break;
            } else {
                System.out.println("Closing date cannot be before opening date!");
            }
        }
    }
}
