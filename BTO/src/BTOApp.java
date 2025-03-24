import java.text.ParseException;
import java.util.ArrayList;

import Project.HDBProject;
import Users.Applicant;
import Users.HDBManager;
import Users.HDBOfficer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
        projects = readObjects("ProjectList.csv", HDBProject::new);

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
                result.add(line.split(","));
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("File not found " + fileName);
        }
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
//    public static ArrayList<HDBManager> readManagers() throws IOException {
//        return readObjects("ManagerList.csv", HDBManager::new);
//    }
//    public static ArrayList<HDBManager> readManagers() throws IOException {
//        ArrayList<HDBManager> result = new ArrayList<HDBManager>();
//        ArrayList<String[]> fileData = readFile("ManagerList.csv");
//        for (String[] value : fileData) {
//            result.add(new HDBManager(value));
//        }
//
//        return result;
//    }

//    public static ArrayList<HDBOfficer> readOfficer() throws IOException {
//        ArrayList<HDBOfficer> result = new ArrayList<HDBOfficer>();
//        ArrayList<String[]> fileData = readFile("OfficerList.csv");
//        for (String[] value : fileData) {
//            result.add(new HDBOfficer(value));
//        }
//
//        return result;
//    }

//    public static ArrayList<Applicant> readApplicant() throws IOException {
//        ArrayList<Applicant> result = new ArrayList<Applicant>();
//        ArrayList<String[]> fileData = readFile("ApplicantList.csv");
//        for (String[] value : fileData) {
//            result.add(new Applicant(value));
//        }
//
//        return result;
//    }
//
//    public static ArrayList<HDBProject> readProjects() throws IOException, ParseException {
//        ArrayList<HDBProject> result = new ArrayList<HDBProject>();
//        ArrayList<String[]> fileData = readFile("ProjectList.csv");
//        for (String[] value : fileData) {
//            result.add(new HDBProject(value));
//        }
//        return result;
//    }




}
