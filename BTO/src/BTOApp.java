import java.text.ParseException;
import java.util.ArrayList;

import Project.HDBProject;
import Users.Applicant;
import Users.HDBManager;
import Users.HDBOfficer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

// The throws exception might change at a later date, idk what to do with the throw
// We could also consider since the files should exist that it's not necessary
// also because the exception handling is extra work to consider (see how?)

// TODO check if there is an easier way to create objects from files in 1 function passing in fileName as a parameter
// Can use generics but seems like cannot create objects of some generic type
// Possible to find work around but tentatively we keep as is (just minor scalability problem, if you want to read more files for some reason)

public class BTOApp {
    private static ArrayList<Applicant> applicants = new ArrayList<Applicant>();
    private static ArrayList<HDBOfficer> officers = new ArrayList<HDBOfficer>();
    private static ArrayList<HDBManager> managers = new ArrayList<HDBManager>();
    private static ArrayList<HDBProject> projects = new ArrayList<HDBProject>();

    public static void main(String[] args) throws IOException, ParseException {
        // Class::new is a method reference to the constructor
        applicants = readObjects("ApplicantList.csv", Applicant::new);
        officers = readObjects("OfficerList.csv", HDBOfficer::new);
        managers = readObjects("ManagerList.csv", HDBManager::new);
        projects = readProjects(managers, officers);

//        System.out.println("Applicants: ");
//        System.out.println(applicants.toString());
//        System.out.println("Officers: ");
//        System.out.println(officers.toString());
//        System.out.println("Managers: ");
//        System.out.println(managers.toString());
//        System.out.println("Projects: ");
//        System.out.println(projects.toString());

    }

    public static ArrayList<String[]> readFile(String fileName) throws IOException {
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
    private static String[] parseCSVLine(String line) {
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

    // We want the return type to be of type T
    // We pass in the fileName as well as the constructor of the Class we want to create
    public static <T> ArrayList<T> readObjects(String fileName, ThrowingFunction<T> constructor) throws IOException {
        ArrayList<T> result = new ArrayList<>();
        ArrayList<String[]> fileData = readFile(fileName);

        for (String[] value : fileData) {
            try {
                // We create the objects with the values for each row of data in fileData
                result.add(constructor.apply(value));
            } catch (Exception e) {
                System.err.println("Error parsing data from file '" + fileName + "': " + String.join(",", value));
                e.printStackTrace();
            }
        }

        return result;
    }

    // We need to specially handle readProjects since we need to pass in all HDBManager and HDBOfficer
    public static ArrayList<HDBProject> readProjects(ArrayList<HDBManager> managers, ArrayList<HDBOfficer> officers) throws IOException, ParseException {
        ArrayList<HDBProject> result = new ArrayList<HDBProject>();
        ArrayList<String[]> fileData = readFile("ProjectList.csv");

        for (String[] value : fileData) {
            ArrayList<HDBOfficer> projectOfficers = new ArrayList<HDBOfficer>();
            HDBManager projectManager = null;

            // Identify the manager object that is managing the project
            for (HDBManager manager : managers) {
                if (manager.getName().compareTo(value[10]) == 0) {
                    projectManager = manager;
                }
            }

            // Identify the officers that are assigned to the project
            // We split the values since each officer in value[12] is separated by commas
            String[] officerNames = value[12].split(",");
            for (HDBOfficer officer : officers) {
                for (String assigned : officerNames) {
                    if (officer.getName().compareTo(assigned) == 0) {
                        // If the officer in officers list is the name of the office assigned, we add them to the list of assigned officers
                        projectOfficers.add(officer);
                    }
                }
            }

            result.add(new HDBProject(value, projectManager, projectOfficers));
        }
        return result;
    }

}
