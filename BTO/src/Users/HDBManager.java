package Users;

public class HDBManager extends User implements HDBStaff {

    public HDBManager(String[] values) {
        super(values[0], values[1], Integer.parseInt(values[2]), values[3], values[4]);
    }

    @Override
    public void viewProjects() {
        // Display all projects
    }
}
