package Users;

public class Applicant extends User {


    public Applicant(String name, String nric, int age, String maritalStatus, String password) {
        super(name, nric, age, maritalStatus, password);
    }

    public void viewProjects() {
        // get UserFilter then filter out data
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
