package Validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Provides basic validation methods for commonly used inputs
 */
public interface BasicValidation {
    /**
     * Date format for reading in dates
     */
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");

    /**
     * Scanner object to read inputs
     */
    Scanner sc = new Scanner(System.in);

    /**
     * Get some integer input, checks for valid integer
     * @return integer user input
     */
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

    /**
     * Gets some long input, checks for valid long
     * @return long user input
     */
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

    /**
     * Gets choice for some menu selection
     * @param min the minimum choice that can be made
     * @param max the maximum choice that can be made
     * @return choice of the user
     */
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

    /**
     * Gets a valid date input (dd/mm/yyyy) and converts to a Date object
     * @return date input
     */
    default Date validateDate() {
        String date;
        Date dateObj;
        while (true) {
            // Handle the ParseException
            // Other functions that call this function directly/indirectly will still need to indicate a throw
            try {
                System.out.println("Enter date (dd/mm/yyyy):");
                date = sc.nextLine();
                dateObj = format.parse(date);
                break;
            } catch (ParseException e) {
                System.out.println("Invalid date format!");
            }
        }
        return dateObj;
    }

    /**
     * Gets a valid price (long more than 0)
     * @return user input of price
     */
    default long validatePrice() {
        long price;
        while (true) {
            try {
                price = sc.nextLong();
                sc.nextLine();
                if (price < 1) {
                    System.out.println("Price must be a positive number");
                    continue;
                }
                return price;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid price");
            }
        }
    }

}
