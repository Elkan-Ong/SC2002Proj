package Users;

public class Applicant extends User {


    public Applicant(String[] values) {
        super(values[0], values[1], Integer.parseInt(values[2]), values[3], values[4]);
    }

    public void viewProjects() {
        // get Misc.UserFilter then filter out data
        return;
    }

    public void applyForProject() {
        return;
    }

    public void viewApplication() {
        return;
    }

    public boolean requestWithdrawal() {
        return false;
    }

    public void createQuery() {
        return;
    }

    public void deleteQuery() {
        return;
    }

    public void editQuery() {
        return;
    }

    private void bookFlat() {
        // when applicant's project application status is "booked" then this function becomes available
    }

}
