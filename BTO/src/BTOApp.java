import java.text.ParseException;
import java.util.ArrayList;

import AppInterfaces.ImportFiles;
import AppInterfaces.WriteFiles;
import Project.HDBProject;
import Users.AllUsers;
import Users.Applicant;
import Users.HDBManager;
import Users.HDBOfficer;

import java.io.IOException;
import java.util.List;

// The throws exception might change at a later date, idk what to do with the throw
// We could also consider since the files should exist that it's not necessary
// also because the exception handling is extra work to consider (see how?)

// TODO check if there is an easier way to create objects from files in 1 function passing in fileName as a parameter
// Can use generics but seems like cannot create objects of some generic type
// Possible to find work around but tentatively we keep as is (just minor scalability problem, if you want to read more files for some reason)

public class BTOApp implements ImportFiles, WriteFiles {
    private static AllUsers allUsers = new AllUsers();
    private static List<HDBProject> allProjects = new ArrayList<>();

    public static void main(String[] args) throws IOException, ParseException {
        try {
            readAllFiles();
        } catch (Exception e) {
            return;
        }
        System.out.println(allUsers.getUsers());
        System.out.println(allProjects);
        System.out.println(allProjects.get(10).getAllProjectApplications());
        System.out.println(allProjects.get(10).getQueries().getFirst().getReply());
        System.out.println(allProjects.get(10).getQueries().getLast().getReply());
        System.out.println(allProjects.get(10).getWithdrawals());
    }

    public static void readAllFiles() throws IOException, ParseException {
        try {
            ImportFiles.readUsers(allUsers,"ApplicantList.csv", Applicant::new);
            ImportFiles.readUsers(allUsers,"OfficerList.csv", HDBOfficer::new);
            ImportFiles.readUsers(allUsers,"ManagerList.csv", HDBManager::new);
            allProjects = ImportFiles.readProjects(allUsers);
            ImportFiles.readApplications(allUsers, allProjects);
            ImportFiles.readQuery(allUsers, allProjects);
            ImportFiles.readWithdrawal(allUsers, allProjects);
            ImportFiles.readUnits(allUsers, allProjects);
        } catch (Exception e) {
            System.out.println("An error has occurred while reading files, please ensure provided files are used");
        }
    }






}
