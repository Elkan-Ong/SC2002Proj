package Misc.Filter;

import Project.AvailableFlatTypes;
import Project.HDBProject;
import Validation.BasicValidation;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Gets the user information to create the filter and generate the UserFilter object
 */
public interface CreateFilter extends BasicValidation, AvailableFlatTypes {
    /**
     * Asks user for what conditions they would like to filter the projects
     * Calls the corresponding methods to add conditions to the filter
     * @param allProjects list of all projects to be filtered
     * @return UserFilter object created on the conditions selected
     */
    default UserFilter createFilter(List<HDBProject> allProjects) {
        if (allProjects.isEmpty()) {
            System.out.println("No projects have been created yet!");
            return null;
        }
        Scanner sc = new Scanner(System.in);
        UserFilter filter = new UserFilter();

        int choice;
        while (true) {
            filterMenu();
            while (true) {
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                    if (choice < 1 || choice > 4) {
                        System.out.println("Invalid Input");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    sc.nextLine();
                    return filter;
                }
            }
            switch (choice) {
                case 1:
                    filterNeighbourhoods(allProjects, filter);
                    break;
                case 2:
                    filterFlatTypes(filter);
                    break;
                case 3:
                    filterMaxPrice(filter);
                    break;
                case 4:
                    filterMinPrice(filter);
                    break;
            }
        }
    }

    /**
     * Prompts User with neighbourhoods that can be selected to be filtered
     * @param allProjects list of projects to be filtered
     * @param filter filter object to be updated
     */
    default void filterNeighbourhoods(List<HDBProject> allProjects, UserFilter filter) {
        List<String> neighbourhoods = new ArrayList<>();
        for (HDBProject project : allProjects) {
            if (!neighbourhoods.contains(project.getNeighbourhood())) {
                neighbourhoods.add(project.getNeighbourhood());
            }
        }
        int choice;
        while (true) {
            if (neighbourhoods.isEmpty()) {
                System.out.println("No neighbourhoods left to select");
                return;
            }
            System.out.println("Select which neighbourhoods you would like to filter: (enter non-digit to exit)");
            for (int i=0; i < neighbourhoods.size(); i++) {
                System.out.println((i+1) + ") " + neighbourhoods.get(i));
            }
            try {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 1 || choice > neighbourhoods.size()) {
                    System.out.println("Invalid selection");
                    continue;
                }
                filter.addNeighbourhood(neighbourhoods.get(choice-1));
                neighbourhoods.remove(choice-1);
            } catch (InputMismatchException e) {
                sc.nextLine();
                return;
            }
        }
    }

    /**
     * Prompts User with Flat types that can be filtered
     * @param filter filter object to be object
     */
    default void filterFlatTypes(UserFilter filter) {
        int choice;
        while (true) {
            try {
                System.out.println("Select Flat Types: (enter non-digit to exit");
                for (int i=0; i < availableTypes.length; i++) {
                    System.out.println((i+1) + ") " + availableTypes[i]);
                }
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < 1 || choice > availableTypes.length) {
                    System.out.println("Invalid selection");
                    continue;
                }
                if (!filter.getTypes().contains(availableTypes[choice-1])) {
                    filter.addType(availableTypes[choice-1]);
                } else {
                    System.out.println("Type has already been selected!");
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                return;
            }
        }
    }

    /**
     * Prompts User to enter minimum price of Units they are interested in
     * @param filter filter object to be updated
     */
    default void filterMinPrice(UserFilter filter) {
        System.out.println("Enter Min Price: ");
        long minPrice;
        while (true) {
            minPrice = validatePrice();
            if (filter.getMaxPrice() != -1 && minPrice > filter.getMaxPrice()) {
                System.out.println("Minimum price cannot be more than max price!");
                continue;
            }
            break;
        }
        filter.setMinPrice(minPrice);
    }

    /**
     * Prompts User to enter maximum price of Units they are interested in
     * @param filter filter object to be updated
     */
    default void filterMaxPrice(UserFilter filter) {
        System.out.println("Enter Max Price: ");
        long maxPrice;
        while (true) {
            maxPrice = validatePrice();
            if (filter.getMinPrice() != -1 && maxPrice < filter.getMinPrice()) {
                System.out.println("Maximum price cannot be less than minimum price!");
                continue;
            }
            break;
        }
        filter.setMaxPrice(maxPrice);
    }

    /**
     * Displays the menu for selection of different conditions to filter by
     */
    default void filterMenu() {
        System.out.println("What would you like to filter? (enter non-digit to exit)");
        System.out.println("1) Neighbourhoods");
        System.out.println("2) Flat Types");
        System.out.println("3) Maximum Price");
        System.out.println("4) Minimum Price");
    }
}
