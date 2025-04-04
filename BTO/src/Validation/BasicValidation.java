package Validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public interface BasicValidation {
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
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

}
