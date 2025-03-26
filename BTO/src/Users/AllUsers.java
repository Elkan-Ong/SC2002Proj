package Users;


import java.util.ArrayList;

public class AllUsers {
    private ArrayList<User> users = new ArrayList<User>();

    public void addUser(User user) {
        this.users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

}
