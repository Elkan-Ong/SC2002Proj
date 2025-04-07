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
import java.util.Arrays;
import java.util.Scanner;



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
