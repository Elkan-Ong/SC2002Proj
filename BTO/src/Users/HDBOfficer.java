package Users;

public class HDBOfficer extends Applicant implements HDBStaff {

    public HDBOfficer(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }

}
