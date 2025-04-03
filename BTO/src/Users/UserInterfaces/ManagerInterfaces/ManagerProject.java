package Users.UserInterfaces.ManagerInterfaces;

import Project.AvailableFlatTypes;
import Project.Flat;
import Project.HDBProject;
import Project.ProjectApplication;
import Users.HDBManager;
import Validation.BasicValidation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public interface ManagerProject extends FlatTypeSelection, BasicValidation, AvailableFlatTypes {
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
    Scanner sc = new Scanner(System.in);

    void deleteProject();

    default Date validateDate() {
        String date;
        Date dateObj;
        while (true) {
            // Handle the ParseException
            // Other functions that call this function directly/indirectly will still need to indicate a throw
            try {
                System.out.println("Enter date (dd/mm/yy):");
                date = sc.nextLine();
                dateObj = format.parse(date);
                break;
            } catch (ParseException e) {
                System.out.println("Invalid date format!");
            }
        }
        return dateObj;
    }

    default String getProjectName(List<HDBProject> allProjects) {
        System.out.println("Enter project name:");
        String name;
        boolean sameName = false;
        do {
            name = sc.nextLine();
            for (HDBProject project : allProjects) {
                if (name.equals(project.getName())) {
                    System.out.println("Project name cannot be same as existing project!");
                    System.out.println("Please enter a new name:");
                    sameName = true;
                    break;
                }
            }
        } while (sameName);
        return name;
    }

    default String getNeighbourhood() {
        System.out.println("Enter neighbourhood:");
        return sc.nextLine();
    }

    default HDBProject getProjectDetailsAndCreate(HDBManager manager, List<HDBProject> allProjects) throws ParseException {
        String projectName = getProjectName(allProjects);
        String neighbourhood = getNeighbourhood();

        System.out.println("Select First Type of Flat:");
        String type1 = getFlatType();
        System.out.println("Enter number of units of "+ type1 + " flat:");
        int units1 = getInt();
        System.out.println("Enter price of "+ type1 + " flat:");
        long price1 = getLong();

        System.out.println("Select Second Type of Flat:");
        String type2 = getFlatType();
        while (type1.equals(type2)) {
            System.out.println("First and Second Flat Types are the same!");
            System.out.println("Please select a different type!");
            type2 = getFlatType();
        }
        System.out.println("Enter number of units of "+ type2 + " flat:");
        int units2 = getInt();

        System.out.println("Enter price of "+ type2 + " flat:");
        long price2 = getLong();

        System.out.println("Enter opening date:");
        Date openingDate = validateDate();

        System.out.println("Enter closing date:");
        Date closingDate = validateDate();


        while (closingDate.before(openingDate) || closingDate.equals(openingDate)) {
            System.out.println("Closing date cannot be before or same as opening date!");
            System.out.println("Enter new closing date (dd/mm/yy):");
            closingDate = validateDate();
        }

        System.out.println("Enter number of officer slots:");
        int officerSlots = getInt();
        while (officerSlots < 0 || officerSlots > 10) {
            System.out.println("Invalid number of officer slots!");
            System.out.println("Enter number of officer slots:");
            officerSlots = getInt();
        }



        HDBProject newProject = new HDBProject(projectName, neighbourhood,
                type1, units1, price1,
                type2, units2, price2,
                openingDate, closingDate,
                manager, officerSlots);

        manager.addOldProject(newProject);

        return newProject;
    }

    void viewCurrentProject();

    default void editMenu() {
        System.out.println("What would you like to edit?");
        System.out.println("1) Project Name");
        System.out.println("2) Neighbourhood");
        System.out.println("3) Flat Type/Units");
        System.out.println("4) Opening Date");
        System.out.println("5) Closing Date");
        System.out.println("6) Officer slots");
        System.out.println("To cancel, enter a non-number character");
    }

    default void editProject(HDBProject project) {
        editMenu();
        int choice;
        try {
            choice = sc.nextInt();
            while (choice < 1 || choice > 6) {
                System.out.println("Invalid selection!");
                System.out.println("Enter selection:");
                choice = sc.nextInt();
            }
            sc.nextLine(); // clear buffer in case
        } catch (InputMismatchException e) {
            return;
        }
        switch (choice) {
            case 1:
                editProjectName(project);
                break;
            case 2:
                editNeighbourhood(project);
                break;
            case 3:
                editFlat(project);
                break;
            case 4:
                editOpeningDate(project);
                break;
            case 5:
                editClosingDate(project);
                break;
            case 6:
                editOfficerSlots(project);
                break;
        }
        project.displayProjectStaff();
    }

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
        int minUnits = flat.getBookedUnits();
        System.out.println("Note: " + minUnits + " units have been booked");
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
