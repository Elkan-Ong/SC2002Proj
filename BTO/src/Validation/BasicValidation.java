package Validation;

import java.util.InputMismatchException;
import java.util.Scanner;

public interface BasicValidation {
    Scanner sc = new Scanner(System.in);
    default int getInt() {
        int result;
        while (true) {
            try {
                result = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!");
            }
        }
        sc.nextLine();
        return result;
    }

    default long getLong() {
        long result;
        while (true) {
            try {
                result = sc.nextLong();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!");
            }
        }
        sc.nextLine();
        return result;
    }

    default int getChoice(int min, int max) {
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice < min || choice > max) {
                    System.out.println("Invalid Selection!");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid Selection!");
            }
        }
        return choice;
    }

}
