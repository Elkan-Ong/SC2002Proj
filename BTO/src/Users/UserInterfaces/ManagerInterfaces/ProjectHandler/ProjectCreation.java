package Users.UserInterfaces.ManagerInterfaces.ProjectHandler;

import Project.HDBProject;
import Users.HDBManager;
import Validation.BasicValidation;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Interface for when a Manager wants to create a brand-new project
 */
public interface ProjectCreation extends BasicValidation, FlatTypeSelection {
    Scanner sc = new Scanner(System.in);

    /**
     * Gathers the details of the Project to be created
     * @param manager Manager creating the project
     * @param allProjects all the projects created
     */
    default void getProjectDetailsAndCreate(HDBManager manager, List<HDBProject> allProjects) {
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
        Date currentDate = new Date();
        while (openingDate.before(currentDate)) {
            System.out.println("Project must only be open after today");
            System.out.println("Enter new opening date (dd/mm/yy):");
            openingDate = validateDate();
        }

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
        manager.setProject(newProject);
        manager.addOldProject(newProject);
        allProjects.add(newProject);
    }

    /**
     * Gets and checks the project name against all existing project
     * names must be unique
     * @param allProjects all projects created
     * @return name of the project
     */
    default String getProjectName(List<HDBProject> allProjects) {
        System.out.println("Enter project name:");
        String name;

        boolean sameName;
        do {
            sameName = false;
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

    /**
     * Gets the neighbourhood the Project will be built in
     * @return name of the neighbourhood
     */
    default String getNeighbourhood() {
        System.out.println("Enter neighbourhood:");
        return sc.nextLine();
    }
}
