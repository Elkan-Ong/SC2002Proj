package Users.UserInterfaces.ManagerInterfaces;

import Project.Flat;
import Project.HDBProject;
import Users.HDBManager;
import Validation.BasicValidation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public interface ManagerProject extends FlatTypeSelection, BasicValidation {
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

    default String getProjectName() {
        System.out.println("Enter project name:");
        return sc.nextLine();
    }

    default String getNeighbourhood() {
        System.out.println("Enter neighbourhood:");
        return sc.nextLine();
    }

    default HDBProject getProjectDetailsAndCreate(HDBManager manager) throws ParseException {
        String projectName = getProjectName();
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
        sc.nextLine(); // clear input buffer to read date

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
        sc.nextLine(); // clear input buffer in case

        return createProjectObject(projectName, neighbourhood,
                type1, units1, price1,
                type2, units2, price2,
                openingDate, closingDate,
                manager, officerSlots);
    }

    default HDBProject createProjectObject(String projectName, String neighbourhood,
                                     String type1, int units1, long price1,
                                     String type2, int units2, long price2,
                                     Date openingDate, Date closingDate,
                                     HDBManager manager, int officerSlots) {
        return new HDBProject(projectName, neighbourhood,
                type1, units1, price1,
                type2, units2, price2,
                openingDate, closingDate,
                manager, officerSlots);
    }

    void viewCurrentProject();

    default void editMenu() {
        System.out.println("What would you like to edit?");
        System.out.println("1) Project Name");
        System.out.println("2) Opening Date");
        System.out.println("3) Closing Date");
        System.out.println("To cancel, enter a non-number character");
    }

    default void editProject(HDBProject project) {
        editMenu();
        int choice;
        try {
            choice = sc.nextInt();
            while (choice < 1 || choice > 3) {
                System.out.println("Invalid selection!");
                System.out.println("Enter selection: ");
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
                editOpeningDate(project);
                break;
            case 3:
                editClosingDate(project);
                break;
        }
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
