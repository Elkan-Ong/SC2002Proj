package Users;

import Users.UserInterfaces.Application;
import Users.UserInterfaces.Query;

public class Applicant extends User implements Application, Query {


    public Applicant(String[] values) {
        super(values[0], values[1], Integer.parseInt(values[2]), values[3], values[4]);
    }

    @Override
    public void viewProjects() {
        // get Misc.UserFilter then filter out data
        return;
    }
//

    @Override
    public void applyForProject() {

    }

    @Override
    public void viewApplication() {

    }

    @Override
    public boolean requestWithdrawal() {
        return false;
    }

    @Override
    public void bookFlat() {

    }

    @Override
    public void createQuery() {

    }

    @Override
    public void deleteQuery() {

    }

    @Override
    public void editQuery() {

    }
}
