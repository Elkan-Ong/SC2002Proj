import java.text.ParseException;
import java.util.ArrayList;

import AppInterfaces.ImportFiles;
import Misc.Query;
import Misc.UserFilter;
import Project.HDBProject;
import Users.AllUsers;
import Users.Applicant;
import Users.HDBManager;
import Users.HDBOfficer;

import java.io.IOException;

// The throws exception might change at a later date, idk what to do with the throw
// We could also consider since the files should exist that it's not necessary
// also because the exception handling is extra work to consider (see how?)

// TODO check if there is an easier way to create objects from files in 1 function passing in fileName as a parameter
// Can use generics but seems like cannot create objects of some generic type
// Possible to find work around but tentatively we keep as is (just minor scalability problem, if you want to read more files for some reason)

public class BTOApp implements ImportFiles {
    private static AllUsers allUsers = new AllUsers();
    private static ArrayList<HDBProject> projects = new ArrayList<HDBProject>();
    // might need query data file? (probably good idea)
    // for time now, as long as program doesn't close, login as user -> submit query -> logout -> login as officer -> should be able to view
    private static ArrayList<Query> allQueries = new ArrayList<Query>();

    public static void main(String[] args) throws IOException, ParseException {
        // Class::new is a method reference to the constructor
        // This part can be improved for scalability (if we need to add more file/user types)
        // While it violates OCP, I will leave for now, kind of difficult to implement
        ImportFiles.readObjects(allUsers,"ApplicantList.csv", Applicant::new);
        ImportFiles.readObjects(allUsers,"OfficerList.csv", HDBOfficer::new);
        ImportFiles.readObjects(allUsers,"ManagerList.csv", HDBManager::new);
        projects = ImportFiles.readProjects(allUsers);
//        System.out.println("Applicants: ");
//        System.out.println(applicants.toString());
//        System.out.println("Officers: ");
//        System.out.println(officers.toString());
//        System.out.println("Managers: ");
//        System.out.println(managers.toString());
//        System.out.println("Projects: ");
//        System.out.println(projects.toString());

    }






}
