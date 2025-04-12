package Project.ProjectInterfaces.FlatInterface;

import Users.Applicant;

/**
 * Handles the booking of the Unit
 */
public interface BookUnitHandler {
    /**
     * Assigns a Unit to an Applicant when they request to book a Unit
     * @param applicant Applicant to assign a Unit to
     */
    void assignUnit(Applicant applicant);
}
