package Users.UserInterfaces.ManagerInterfaces;

import Project.AvailableFlatTypes;

import java.util.Scanner;

public interface FlatTypeSelection extends AvailableFlatTypes {
    Scanner sc = new Scanner(System.in);

    default String getFlatType() {
        System.out.println("Select Flat Type:");
        for (int i=0; i < AvailableFlatTypes.availableTypes.length; i++) {
            System.out.println((i+1) + ") " + AvailableFlatTypes.availableTypes[i]);
        }
        int choice = sc.nextInt();
        while (choice < 1 || choice > AvailableFlatTypes.availableTypes.length) {
            System.out.println("Invalid choice!");
            System.out.println("Enter Flat Type:");
            choice = sc.nextInt();
        }
        sc.nextLine();
        return AvailableFlatTypes.availableTypes[choice-1];
    }
}
