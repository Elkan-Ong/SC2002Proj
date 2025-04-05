package AppInterfaces;

import Enums.ApplicationStatus;
import Enums.RegistrationStatus;
import Misc.Query;
import Misc.WithdrawApplication;
import Project.Flat;
import Project.HDBProject;
import Project.ProjectApplication;
import Users.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface ImportFiles {

    static List<String[]> readFile(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("BTO/src/Files/" + fileName))) {
            String line;
            List<String[]> result = new ArrayList<String[]>();
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                result.add(parseCSVLine(line));
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("File not found " + fileName);
        }
    }

    // This function is primarily for ProjectList
    // The Officer column will be the assigned officers names, in quotations, separated by commas
    // this causes a problem when using .split(","), therefore we need to create a way to keep it as 1 string
    // we have to parse the column value, identify quotations and keeping the string whole
    static String[] parseCSVLine(String line) {
        // Token represents each value in a cell
        List<String> tokens = new ArrayList<>();
        // We use StringBuilder as it allows us to create a String (since String objects are normally immutable)
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            // If we see quotes, we toggle state
            // If inQuotes is true, it ignores commas until the string is closed with another set of quotations
            // If inQuotes is false, when we encounter a comma, we know that is the end of the token
            if (c == '\"') {
                inQuotes = !inQuotes; // toggle quote state
            } else if (c == ',' && !inQuotes) {
                tokens.add(sb.toString().trim());
                sb.setLength(0); // reset StringBuilder
            } else {
                sb.append(c);
            }
        }
        tokens.add(sb.toString().trim()); // add the last field
        return tokens.toArray(new String[0]);
    }


    // We need to specially handle readProjects since we need to pass in all HDBManager and HDBOfficer
    // Warning: Downcasting here is dependent on teh fact that usernames are unique and the respective name is correct
    // The safety net is that only users of those types are able to reach that point in the first place
    // e.g. only Managers can be of type HDBManager
    // Officers may be problematic if they revert back to Applicants however, if after a project ends
    // Unless for implementation we don't revert them but rather keep them idle until assigned a new project?
    static List<HDBProject> readProjects(AllUsers allUsers) throws IOException, ParseException {
        List<HDBProject> result = new ArrayList<>();
        List<String[]> fileData = readFile("ProjectList.csv");

        for (String[] value : fileData) {
            List<HDBOfficer> projectOfficers = new ArrayList<>();
            HDBManager projectManager = null;

            // Identify the manager object that is managing the project
            for (User manager : allUsers.getUsers()) {
                if (manager.getName().compareTo(value[10]) == 0) {
                    projectManager = (HDBManager) manager;
                    break;
                }
            }

            // Identify the officers that are assigned to the project
            // We split the values since each officer in value[12] is separated by commas
            String[] officerNames = value[12].split(",");
            for (User officer : allUsers.getUsers()) {
                for (String assigned : officerNames) {
                    if (officer.getName().compareTo(assigned) == 0) {
                        // If the officer in officers list is the name of the office assigned, we add them to the list of assigned officers
                        projectOfficers.add((HDBOfficer) officer);
                    }
                }
            }
            HDBProject temp = new HDBProject(value, projectManager, projectOfficers);
            projectManager.addOldProject(temp);
            Date date = new Date();
            if (temp.getClosingDate().after(date)) {
                projectManager.setProject(temp);
                // TODO set for officer as well
            }
            result.add(temp);
        }
        return result;
    }

    // We want the return type to be of type T
    // We pass in the fileName as well as the constructor of the Class we want to create
    static <T> void readUsers(AllUsers allUsers, String fileName, ThrowingFunction<T> constructor) throws IOException {
        List<T> result = new ArrayList<>();
        List<String[]> fileData = readFile(fileName);

        for (String[] value : fileData) {
            try {
                // We create the objects with the values for each row of data in fileData
                allUsers.addUser((User)constructor.apply(value));
            } catch (Exception e) {
                System.err.println("Error parsing data from file '" + fileName + "': " + String.join(",", value));
                e.printStackTrace();
            }
        }

    }

    static void readApplications(AllUsers allUsers, List<HDBProject> allProjects) throws IOException {
        List<String[]> fileData = readFile("ApplicationList.csv");

        for (String[] value : fileData) {
            Applicant applicant = null;
            for (User user : allUsers.getUsers()) {
                if (value[0].equals(user.getName())) {
                    applicant = (Applicant) user;
                }
            }
            for (HDBProject project : allProjects) {
                if (value[1].equals(project.getName())) {
                    Flat selectedFlat = null;
                    for (Flat flat : project.getFlatType()) {
                        if (flat.getType().equals(value[2])) {
                            selectedFlat = flat;
                        }
                    }
                    ProjectApplication application = new ProjectApplication(applicant, project, selectedFlat);
                    switch (value[3]) {
                        case "Pending":
                            application.setStatus(ApplicationStatus.PENDING);
                            break;
                        case "Successful":
                            application.setStatus(ApplicationStatus.SUCCESSFUL);
                            break;
                        case "Unsuccessful":
                            application.setStatus(ApplicationStatus.UNSUCCESSFUL);
                            break;
                        case "Booked":
                            application.setStatus(ApplicationStatus.BOOKED);
                            break;
                    }
                    project.addApplication(application);
                }
            }
        }
    }

    static void readQuery(AllUsers allUsers, List<HDBProject> allProjects) throws IOException, ParseException {
        List<String[]> fileData = readFile("QueryList.csv");
        for (String[] value : fileData) {
            Applicant applicant = null;
            for (User user : allUsers.getUsers()) {
                if (value[3].equals(user.getName())) {
                    applicant = (Applicant) user;
                }
            }
            for (HDBProject project : allProjects) {
                if (value[4].equals(project.getName())) {
                    Query query = new Query(applicant, project, value[0], value[1], value[5]);
                    if (!value[2].isEmpty()) {
                        query.setReply(value[2]);
                    }
                    project.addQuery(query);
                }
            }
        }
    }

    static void readWithdrawal(AllUsers allUsers, List<HDBProject> allProjects) throws IOException {
        List<String[]> fileData = readFile("WithdrawalList.csv");
        for (String[] value : fileData) {
            Applicant applicant = null;
            for (User user : allUsers.getUsers()) {
                if (value[0].equals(user.getName())) {
                    applicant = (Applicant) user;
                }
            }
            for (HDBProject project : allProjects) {
                if (value[1].equals(project.getName())) {
                    ProjectApplication userApplication = null;
                    for (ProjectApplication application : project.getAllProjectApplications()) {
                        if (application.getApplicant().getName().equals(value[0])) {
                            userApplication = application;
                        }
                    }
                    WithdrawApplication withdrawal = new WithdrawApplication(applicant, userApplication);
                    switch (value[2]) {
                        case "Pending":
                            withdrawal.setStatus(RegistrationStatus.PENDING);
                            break;
                        case "Successful":
                            withdrawal.setStatus(RegistrationStatus.SUCCESSFUL);
                            break;
                        case "Unsuccessful":
                            withdrawal.setStatus(RegistrationStatus.UNSUCCESSFUL);
                            break;
                    }
                    project.addWithdrawal(withdrawal);
                }
            }
        }
    }

    static void readUnits(AllUsers allUsers, List<HDBProject> allProjects) throws IOException {
        List<String[]> fileData = readFile("WithdrawalList.csv");
        for (String[] value : fileData) {
            Applicant applicant = null;
            for (User user : allUsers.getUsers()) {
                if (user.getName().equals(value[2])) {
                    applicant = (Applicant) user;
                }
            }
            for (HDBProject project : allProjects) {
                if (project.getName().equals(value[0])) {
                    for (Flat flat : project.getFlatType()) {
                        if (flat.getType().equals(value[1])) {
                            flat.getUnits().get(Integer.parseInt(value[3])-1).setBooked(applicant);
                        }
                    }
                }
            }
        }
    }

}
