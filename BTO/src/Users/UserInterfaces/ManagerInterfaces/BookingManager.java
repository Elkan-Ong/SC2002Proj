 

import Users.Applicant;

public class BookingManager {
    private Booking[] bookings;
    private int size;

    public BookingManager() {
        this.bookings = new Booking[100]; 
        this.size = 0;
    }

    public void addBooking(Booking booking) {
        if (size == bookings.length) {
            expandArray();
        }
        bookings[size++] = booking;
    }

    public boolean hasBooked(Applicant applicant) {
        for (int i = 0; i < size; i++) {
            if (bookings[i].getApplicant().getNric().equals(applicant.getNric())) {
                return true;
            }
        }
        return false;
    }

    public Booking getBookingByNRIC(String nric) {
        for (int i = 0; i < size; i++) {
            if (bookings[i].getApplicant().getNric().equalsIgnoreCase(nric)) {
                return bookings[i];
            }
        }
        return null;
    }

    public void printBookingReceipt(Applicant applicant) {
        Booking booking = getBookingByNRIC(applicant.getNric());
        if (booking != null) {
            System.out.println(booking);
        } else {
            System.out.println("No booking found for this applicant.");
        }
    }

    public void removeBookingByNRIC(String nric) {
        for (int i = 0; i < size; i++) {
            if (bookings[i].getApplicant().getNric().equalsIgnoreCase(nric)) {
                for (int j = i; j < size - 1; j++) {
                    bookings[j] = bookings[j + 1];
                }
                bookings[--size] = null;
                break;
            }
        }
    }

    private void expandArray() {
        Booking[] newArray = new Booking[bookings.length * 2];
        for (int i = 0; i < bookings.length; i++) {
            newArray[i] = bookings[i];
        }
        bookings = newArray;
    }

    public int getSize() {
        return size;
    }

    public Booking[] getAllBookings() {
        Booking[] trimmed = new Booking[size];
        for (int i = 0; i < size; i++) {
            trimmed[i] = bookings[i];
        }
        return trimmed;
    }
}
