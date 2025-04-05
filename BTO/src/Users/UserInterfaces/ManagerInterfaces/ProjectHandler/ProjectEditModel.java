package Users.UserInterfaces.ManagerInterfaces.ProjectHandler;

import Project.AvailableFlatTypes;
import Project.Flat;
import Project.HDBProject;
import Project.Unit;
import Validation.BasicValidation;

import java.util.Date;
import java.util.List;

public interface ProjectEditModel extends BasicValidation, AvailableFlatTypes {
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
            editFlatType(selectedFlat);
        } else {
            editUnits(selectedFlat);
        }
    }

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

    default void editFlatType(Flat flat) {
        System.out.println("Select flat type to change to:");
        for (int i=0; i < availableTypes.length; i++) {
            System.out.println((i+1) + ") " + availableTypes[i]);
        }
        int choice = getChoice(1, availableTypes.length);
        flat.setType(availableTypes[choice-1]);
    }

    default void editNeighbourhood(HDBProject project) {
        System.out.println("Enter new neighbourhood name:");
        String newNeighbourhood = sc.nextLine();
        project.setNeighbourhood(newNeighbourhood);
        System.out.println("Successfully updated neighbourhood.");
    }


    default void editOfficerSlots(HDBProject project) {
        System.out.println("Enter new number of officer slots:");
        if (!project.getAssignedOfficers().isEmpty()) {
            System.out.println("Note: No. of slots cannot be less than " + project.getAssignedOfficers().size() + " without unassigning officers first.");
        }
        int newSlots = getChoice(project.getAssignedOfficers().size(), Integer.MAX_VALUE);
        project.setAvailableOfficerSlots(newSlots);
    }

    default void editProjectName(HDBProject project) {
        System.out.println("Enter new project name:");
        String name = sc.nextLine();
        project.setName(name);
        System.out.println("Project name successfully updated!");
    }


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
