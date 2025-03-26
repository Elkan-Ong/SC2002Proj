package Users;

import Users.UserInterfaces.HDBStaff;

public class HDBOfficer extends Applicant implements HDBStaff {

    public HDBOfficer(String[] values) {
        super(values);
    }

    @Override
    public void viewProjects() {
        // Display all projects
    }

    @Override
    public void displayMenu() {

    }

}
