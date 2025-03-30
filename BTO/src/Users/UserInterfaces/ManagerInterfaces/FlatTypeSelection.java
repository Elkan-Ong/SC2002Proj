package Users.UserInterfaces.ManagerInterfaces;

import java.util.Scanner;

public interface FlatTypeSelection {
    String[] validTypes = {"2-Room", "3-Room", "4-Room", "5-Room"};
    Scanner sc = new Scanner(System.in);

    default String getFlatType() {
        System.out.println("Select Flat Type:");
        for (int i=0; i < validTypes.length; i++) {
            System.out.println((i+1) + ") " + validTypes[i]);
        }
        int choice = sc.nextInt();
        while (choice < 1 || choice > validTypes.length) {
            System.out.println("Invalid choice!");
            System.out.println("Enter Flat Type:");
            choice = sc.nextInt();
        }
        sc.nextLine();
        return validTypes[choice-1];
    }
}
