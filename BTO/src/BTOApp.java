import java.text.ParseException;
import java.util.ArrayList;

import AppInterfaces.ImportFiles;
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
    private static AllUsers allUsers;
    private static ArrayList<HDBProject> projects = new ArrayList<HDBProject>();

    public static void main(String[] args) throws IOException, ParseException {
        // Class::new is a method reference to the constructor
        ImportFiles.readObjects(allUsers,"ApplicantList.csv", Applicant::new);
        ImportFiles.readObjects(allUsers,"OfficerList.csv", HDBOfficer::new);
        ImportFiles.readObjects(allUsers,"ManagerList.csv", HDBManager::new);
        ImportFiles.readProjects(allUsers);

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
