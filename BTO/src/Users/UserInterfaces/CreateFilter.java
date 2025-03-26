package Users.UserInterfaces;

import Misc.UserFilter;

public interface CreateFilter {
    default UserFilter createFilter() {
        // Need to get a list of all possible neighbourhoods/types/other data we filter by
        // Ask user to input all the choices they want then create
        // probably change to ArrayList instead of 1 string
        return new UserFilter("Ang Mo Kio", "2-Room");
    }
}
