public class Main {
    public static void main(String[] args) {


        Project[] projectList = new Project[2];
        projectList[0] = new Project("Acacia Breeze", "Yishun", "2-Room", 2, 350000, "3-Room", 3, 450000, 
                                     "15-02-2025", "20-03-2025", "Jessica", 3, "Daniel, Emily");
        projectList[1] = new Project("Breezy Heights", "Bedok", "2-Room", 5, 300000, "3-Room", 4, 400000, 
                                     "01-03-2025", "30-04-2025", "Amanda", 2, "Michael, Rachel");

        Applicant[] applicantList = new Applicant[5];
        applicantList[0] = new Applicant("John", "S1234567A", 35, "Single", "password");
        applicantList[1] = new Applicant("Sarah", "T7654321B", 40, "Married", "password");
        applicantList[2] = new Applicant("Grace", "S9876543C", 37, "Married", "password");
        applicantList[3] = new Applicant("James", "T2345678D", 30, "Married", "password");
        applicantList[4] = new Applicant("Rachel", "S3456789E", 25, "Single", "password");

        User user = new User("Michael", "HDB Manager", 30, "Single", "password123");

        ProjectFilter pf = new ProjectFilter(projectList, applicantList, user);
        pf.showFilterMenu();
    }
}
