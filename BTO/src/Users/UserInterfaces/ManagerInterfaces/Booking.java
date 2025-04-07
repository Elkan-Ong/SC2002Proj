 import Project.HDBProject;
import Project.Flat;
import Users.Applicant;

import java.util.Date;

public class Booking {
    private Applicant applicant;
    private HDBProject project;
    private Flat flat;
    private int unitNumber; 
    private Date bookingDate;

    public Booking(Applicant applicant, HDBProject project, Flat flat, int unitNumber) {
        this.applicant = applicant;
        this.project = project;
        this.flat = flat;
        this.unitNumber = unitNumber;
        this.bookingDate = new Date(); 
    }

    
    public Applicant getApplicant() {
        return applicant;
    }

    public HDBProject getProject() {
        return project;
    }

    public Flat getFlat() {
        return flat;
    }

    public int getUnitNumber() {
        return unitNumber;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    
    @Override
    public String toString() {
        return "=========================\n" +
               "Booking Receipt\n" +
               "=========================\n" +
               "Applicant: " + applicant.getName() + " (" + applicant.getNric() + ")\n" +
               "Age: " + applicant.getAge() + "\n" +
               "Marital Status: " + applicant.getMaritalStatus() + "\n\n" +
               "Project: " + project.getName() + "\n" +
               "Neighbourhood: " + project.getNeighbourhood() + "\n" +
               "Flat Type: " + flat.getType() + "\n" +
               (unitNumber > 0 ? "Unit Number: " + unitNumber + "\n" : "") +
               "Booking Date: " + bookingDate + "\n";
    }
}
