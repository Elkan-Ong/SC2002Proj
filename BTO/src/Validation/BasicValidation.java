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
        return result;
    }
}
