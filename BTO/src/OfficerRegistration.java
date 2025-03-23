import Enums.RegistrationStatus;
import Users.Applicant;
import Users.HDBManager;

public class OfficerRegistration {
    private Applicant applicant;
    private RegistrationStatus status;
    private HDBManager approvedBy;

    public OfficerRegistration(Applicant applicant) {
        this.applicant = applicant;
        this.status = RegistrationStatus.PENDING;
    }
}
