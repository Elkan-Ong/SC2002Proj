package Users;


import java.util.ArrayList;
import java.util.List;

/**
 * Represents a master list of all user types
 */
public class AllUsers {
    /**
     * List to store all the different users
     */
    private final List<User> users = new ArrayList<>();

    /**
     * Adds user to users list
     * @param user User to be inserted into list
     */
    public void addUser(User user) {
        this.users.add(user);
    }

    /**
     * Gets the list of all users
     * @return the list of users
     */
    public List<User> getUsers() {
        return users;
    }

}
