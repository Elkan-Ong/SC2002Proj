package Users;

import Project.HDBProject;
import Users.UserInterfaces.HDBStaff;

import java.util.ArrayList;

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

    @Override
    public void viewProjects(ArrayList<HDBProject> allProjects) {

    }
}
