package Users;


import java.util.ArrayList;
import java.util.List;

public class AllUsers {
    private List<User> users = new ArrayList<User>();

    public void addUser(User user) {
        this.users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

}
