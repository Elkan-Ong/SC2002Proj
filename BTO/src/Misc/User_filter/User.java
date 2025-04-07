public class User {
    String name;
    String NRIC;
    int age;
    String maritalStatus;
    String password;

    public User(String name, String NRIC, int age, String maritalStatus, String password) {
        this.name = name;
        this.NRIC = NRIC;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = password;
    }
    
    public void display() {
        System.out.println(name + " | NRIC: " + NRIC + " | Age: " + age + " | " + maritalStatus);
    }
}
