package AppInterfaces;

import Enums.ApplicationStatus;
import Enums.RegistrationStatus;
import Misc.OfficerRegistration;
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

/**
 * Read all the files and creates the objects to store all the data
 */
public interface ImportFiles {
    /**
     * Generic file reading, returns all the data in the file except the header
     * @param fileName name of the file
     * @return data of the file
     */
    static List<String[]> readFile(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("BTO/src/Files/" + fileName))) {
            String line;
            List<String[]> result = new ArrayList<>();
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
            throw new IOException("File not found " + fileName);
        }
    }

    /**
     * Used to obtain values in one cell but separated by commas
     * @param line line of data being split
     * @return split line of data
     */
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


    /**
     * Read all the projects in ProjectList.csv
     * @param allUsers AllUsers object containing all the users of the BTO system
     */
    static void readProjects(AllUsers allUsers, List<HDBProject> allProjects) throws IOException, ParseException {
        List<String[]> fileData = readFile("ProjectList.csv");

        for (String[] value : fileData) {
            List<HDBOfficer> projectOfficers = new ArrayList<>();
            HDBManager projectManager = null;

            // Identify the manager object that is managing the project
            for (User manager : allUsers.getUsers()) {
                if (manager.getNric().compareTo(value[10]) == 0) {
                    projectManager = (HDBManager) manager;
                    break;
                }
            }

            // Identify the officers that are assigned to the project
            // We split the values since each officer in value[12] is separated by commas
            String[] officerNames = value[12].split(",");
            for (User officer : allUsers.getUsers()) {
                for (String assigned : officerNames) {
                    if (officer.getNric().compareTo(assigned) == 0) {
                        // If the officer in officers list is the name of the office assigned, we add them to the list of assigned officers
                        projectOfficers.add((HDBOfficer) officer);
                    }
                }
            }
            HDBProject temp = new HDBProject(value, projectManager, projectOfficers);
            assert projectManager != null;
            projectManager.addOldProject(temp);
            Date date = new Date();
            if (temp.getClosingDate().after(date)) {
                projectManager.setProject(temp);
                for (HDBOfficer officer : projectOfficers) {
                    officer.setAssignedProject(temp);
                }
            }
            allProjects.add(temp);
        }
    }

    /**
     * Reads respective Users file
     * @param allUsers AllUsers object containing all the users in the BTO system
     * @param fileName file storing the data of the different User types
     * @param constructor constructor of the respective User type
     * @param <T> corresponding User type
     */
    static <T> void readUsers(AllUsers allUsers, String fileName, ThrowingFunction<T> constructor) throws IOException {
        List<String[]> fileData = readFile(fileName);

        for (String[] value : fileData) {
            try {
                // We create the objects with the values for each row of data in fileData
                allUsers.addUser((User)constructor.apply(value[0], value[1], Integer.parseInt(value[2]), value[3], value[4]));
            } catch (Exception e) {
                System.err.println("Error parsing data from file '" + fileName + "': " + String.join(",", value));
            }
        }

    }

    /**
     * Reads all the Applications in ApplicationList.csv
     * @param allUsers AllUsers object containing all the users in the BTO system
     * @param allProjects List of all projects in the BTO system
     */
    static void readApplications(AllUsers allUsers, List<HDBProject> allProjects) throws IOException {
        List<String[]> fileData = readFile("ApplicationList.csv");

        for (String[] value : fileData) {
            Applicant applicant = null;
            for (User user : allUsers.getUsers()) {
                if (value[0].equals(user.getNric())) {
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

    /**
     * Reads all the Query's in QueryList.csv
     * @param allUsers AllUsers object containing all the users in the BTO system
     * @param allProjects List of all projects in the BTO system
     */
    static void readQuery(AllUsers allUsers, List<HDBProject> allProjects) throws IOException, ParseException {
        List<String[]> fileData = readFile("QueryList.csv");
        for (String[] value : fileData) {
            Applicant applicant = null;
            for (User user : allUsers.getUsers()) {
                if (value[3].equals(user.getNric())) {
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


    /**
     * Reads all the Withdrawals in WithdrawalList.csv
     * @param allUsers AllUsers object containing all the users in the BTO system
     * @param allProjects List of all projects in the BTO system
     */
    static void readWithdrawal(AllUsers allUsers, List<HDBProject> allProjects) throws IOException {
        List<String[]> fileData = readFile("WithdrawalList.csv");
        for (String[] value : fileData) {
            Applicant applicant = null;
            for (User user : allUsers.getUsers()) {
                if (value[0].equals(user.getNric())) {
                    applicant = (Applicant) user;
                }
            }
            for (HDBProject project : allProjects) {
                if (value[1].equals(project.getName())) {
                    ProjectApplication userApplication = null;
                    for (ProjectApplication application : project.getAllProjectApplications()) {
                        if (application.getApplicant().getNric().equals(value[0])) {
                            userApplication = application;
                        }
                    }
                    WithdrawApplication withdrawal = new WithdrawApplication(applicant, userApplication);

                    project.addWithdrawal(withdrawal);
                }
            }
        }
    }

    /**
     * Reads all the Units in UnitList.csv
     * @param allUsers AllUsers object containing all the users in the BTO system
     * @param allProjects List of all projects in the BTO system
     */
    static void readUnits(AllUsers allUsers, List<HDBProject> allProjects) throws IOException {
        List<String[]> fileData = readFile("WithdrawalList.csv");
        for (String[] value : fileData) {
            Applicant applicant = null;
            for (User user : allUsers.getUsers()) {
                if (user.getNric().equals(value[2])) {
                    applicant = (Applicant) user;
                }
            }
            for (HDBProject project : allProjects) {
                if (project.getName().equals(value[0])) {
                    for (Flat flat : project.getFlatType()) {
                        if (flat.getType().equals(value[1])) {
                            flat.getUnits().get(Integer.parseInt(value[3])-1).setBookedBy(applicant);
                        }
                    }
                }
            }
        }
    }

    /**
     * Reads all the OfficerRegistrations in OfficerRegistrationList.csv
     * @param allUsers AllUsers object containing all the users in the BTO system
     * @param allProjects List of all projects in the BTO system
     */
    static void readRegistrations(AllUsers allUsers, List<HDBProject> allProjects) throws IOException {
        List<String[]> fileData = readFile("RegistrationList.csv");
        for (String[] value : fileData) {
            HDBOfficer officer = null;
            HDBProject registerProject = null;

            for (User user : allUsers.getUsers()) {
                if (user.getNric().equals(value[0])) {
                    officer = (HDBOfficer) user;
                }
            }
            for (HDBProject project : allProjects) {
                if (project.getName().equals(value[1])) {
                    registerProject = project;
                    break;
                }
            }
            OfficerRegistration registration = new OfficerRegistration(officer, registerProject);
            switch (value[2]) {
                case "Pending":
                    registration.setStatus(RegistrationStatus.PENDING);
                    break;
                case "Successful":
                    registration.setStatus(RegistrationStatus.SUCCESSFUL);
                    break;
                case "Unsuccessful":
                    registration.setStatus(RegistrationStatus.UNSUCCESSFUL);
                    break;
            }
            assert registerProject != null;
            registerProject.addOfficerRegistration(registration);
        }
    }

    /**
     * Main function to read all the files of the BTO system
     * @param allUsers AllUsers object storing all the Users in the BTO system
     * @param allProjects List containing all projects in the BTO System
     */
    static void readAllFiles(AllUsers allUsers, List<HDBProject> allProjects) {
        try {
            ImportFiles.readUsers(allUsers,"ApplicantList.csv", Applicant::new);
            ImportFiles.readUsers(allUsers,"OfficerList.csv", HDBOfficer::new);
            ImportFiles.readUsers(allUsers,"ManagerList.csv", HDBManager::new);
            ImportFiles.readProjects(allUsers, allProjects);
            ImportFiles.readApplications(allUsers, allProjects);
            ImportFiles.readQuery(allUsers, allProjects);
            ImportFiles.readWithdrawal(allUsers, allProjects);
            ImportFiles.readUnits(allUsers, allProjects);
            ImportFiles.readRegistrations(allUsers, allProjects);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("An error has occurred while reading files, please ensure provided files are used");
        }
    }

}
