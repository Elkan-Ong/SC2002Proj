package Users;

public class HDBManager extends User implements HDBStaff {

    public HDBManager(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }

    @Override
    public void viewProjects() {
        // Display all projects
    }
}
