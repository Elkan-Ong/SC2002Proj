package Users.UserInterfaces.OfficerInterfaces;

import Project.ProjectApplication;

/**
 * Contains methods that are used to handle flat bookings
 */
public interface FlatBookingHandler {
    /**
     * Books a flat for all Applicants that has a successful application
     */
    void flatBooking();

    /**
     * Books a flat for an Applicant that has a successful application
     * Applicant is searched by their NRIC
     */
    void flatBookingByNRIC();

    /**
     * Handles the actual booking of the flat
     * @param application application to get a flat
     * @return boolean indicating a flat was successfully booked
     */
    boolean bookFlat(ProjectApplication application);
}
