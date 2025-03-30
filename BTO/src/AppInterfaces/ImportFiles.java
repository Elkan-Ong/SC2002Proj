package AppInterfaces;

import Project.HDBProject;
import Users.AllUsers;
import Users.HDBManager;
import Users.HDBOfficer;
import Users.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public interface ImportFiles {

    static ArrayList<String[]> readFile(String fileName) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("BTO/src/Files/" + fileName))) {
            String line;
            ArrayList<String[]> result = new ArrayList<String[]>();
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
        ArrayList<String> tokens = new ArrayList<>();
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
    static ArrayList<HDBProject> readProjects(AllUsers allUsers) throws IOException, ParseException {
        ArrayList<HDBProject> result = new ArrayList<>();
        ArrayList<String[]> fileData = readFile("ProjectList.csv");

        for (String[] value : fileData) {
            ArrayList<HDBOfficer> projectOfficers = new ArrayList<>();
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
            if (temp.getClosingDate().before(date)) {
                projectManager.setProject(temp);
            }
            result.add(temp);
        }
        return result;
    }

    // We want the return type to be of type T
    // We pass in the fileName as well as the constructor of the Class we want to create
    static <T> void readObjects(AllUsers allUsers, String fileName, ThrowingFunction<T> constructor) throws IOException {
        ArrayList<T> result = new ArrayList<>();
        ArrayList<String[]> fileData = readFile(fileName);

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
}
