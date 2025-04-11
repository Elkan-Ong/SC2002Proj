package AppInterfaces;

import Misc.OfficerRegistration;
import Misc.Query;
import Misc.WithdrawApplication;
import Project.Flat;
import Project.HDBProject;
import Project.ProjectApplication;
import Project.Unit;
import Users.*;

import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Defines methods to write to important objects to preserve to respective csv files
 * @author Elkan Ong Han'en
 * @since 2025-4-6
 */
public interface WriteFiles {
    static void writeRegistration(List<HDBProject> allProjects) {
        String fileName = "RegistrationList.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Write header row
            writer.println("applicant, project, status");

            // Write data rows
            for (HDBProject project : allProjects) {
                for (OfficerRegistration registration : project.getOfficerApplications()) {
                    String status = "";
                    switch (registration.getApplicationStatus()) {
                        case PENDING -> status = "Pending";
                        case SUCCESSFUL -> status = "Successful";
                        case UNSUCCESSFUL -> status = "Unsuccessful";
                    }
                    writer.println(registration.getApplicant().getNric() + "," + registration.getAppliedProject().getName() + "," + status);
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to files");
        }
    }


    /**
     * writes Unit information into UnitList.csv
     * @param allProjects all projects created
     */
    static void writeUnit(List<HDBProject> allProjects) {
        String fileName = "UnitList.csv";

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Write header row
            writer.println("project,type,applicant,unit");

            // Write data rows
            for (HDBProject project : allProjects) {
                for (Flat flat : project.getFlatType()) {
                    for (Unit unit : flat.getUnits()) {
                        if (unit.getBooked()) {
                            writer.println(project.getName() + "," + flat.getType() + "," + unit.getBookedBy().getNric() + "," + unit.getUnitID());
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to files");
        }
    }

    /**
     * writes Application information into ApplicationList.csv
     * @param allProjects all projects created
     */
    static void writeApplications(List<HDBProject> allProjects) {
        String fileName = "ApplicationList.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Write header row
            writer.println("applicant,project,type,status");

            // Write data rows
            for (HDBProject project : allProjects) {
                for (ProjectApplication application : project.getAllProjectApplications()) {
                    String status = "";
                    switch (application.getApplicationStatus()) {
                        case BOOKED -> status="Booked";
                        case PENDING -> status="Pending";
                        case SUCCESSFUL -> status="Successful";
                        case UNSUCCESSFUL -> status="Unsuccessful";
                    }
                    writer.println(application.getApplicant().getNric() + "," + project.getName() + "," + application.getSelectedType().getType() + "," + status);
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to files");
        }
    }

    /**
     * writes Query information into QueryList.csv
     * @param allProjects all projects created
     */
    static void writeQuery(List<HDBProject> allProjects) {
        String fileName = "QueryList.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Write header row
            writer.println("title,query,reply,applicant,project,timestamp");

            // Write data rows
            for (HDBProject project : allProjects) {
                for (Query query : project.getQueries()) {
                    String reply = query.getReply() == null ? "" : query.getReply();
                    writer.println(query.getTitle() + "," + query.getQuery() + "," + reply + "," + query.getApplicant().getNric() + "," + project.getName() + "," + query.getTimestamp());
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to files");
        }
    }

    /**
     * writes Withdrawal information into WithdrawalList.csv
     * @param allProjects all projects created
     */
    static void writeWithdrawal(List<HDBProject> allProjects) {
        String fileName = "WithdrawalList.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Write header row
            writer.println("applicant,project,status");

            // Write data rows
            for (HDBProject project : allProjects) {
                for (WithdrawApplication withdrawal : project.getWithdrawals()) {
                    String status = "";
                    switch (withdrawal.getStatus()) {
                        case PENDING -> status="Pending";
                        case SUCCESSFUL -> status="Successful";
                        case UNSUCCESSFUL -> status="Unsuccessful";
                    }
                    writer.println(withdrawal.getApplicant().getNric() + "," + withdrawal.getProjectApplication().getAppliedProject().getName() + "," + status);
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to files");
        }
    }

    /**
     * Handles escaping commas, primarily for HDB Officers due to having comma separated values in a cell
     * @param value value to be escaped
     * @return escaped value
     */
    static String escapeCsv(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = value.replace("\"", "\"\""); // Escape double quotes
            return "\"" + value + "\"";         // Wrap in quotes
        }
        return value;
    }

    /**
     * writes Project information into ProjectList.csv
     * @param allProjects all projects created
     */
    static void writeProject(List<HDBProject> allProjects) {
        String fileName = "ProjectList.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Write header row
            writer.println("Project Name,Neighbourhood,Type 1,Number of units for Type 1,Selling price for Type 1,Type 2,Number of units for Type 2,Selling price for Type 2,Application opening date,Application closing date,Manager,Officer Slot,Officer");

            // Write data rows
            for (HDBProject project : allProjects) {
                Flat firstFlat = project.getFlatType().getFirst();
                Flat secondFlat = project.getFlatType().getLast();
                String officers = "";
                for (HDBOfficer officer : project.getAssignedOfficers()) {
                    if (officers.isEmpty()) {
                        officers = officers.concat(officer.getNric());
                    } else {
                        officers = officers.concat("," + officer.getNric());
                    }
                }
                officers = escapeCsv(officers);
                writer.println(project.getName() + ","
                        + project.getNeighbourhood() + ","
                        + firstFlat.getType() + ","
                        + firstFlat.getNoOfUnits() + ","
                        + firstFlat.getPrice() + ","
                        + secondFlat.getType() + ","
                        + secondFlat.getNoOfUnits() + ","
                        + secondFlat.getPrice() + ","
                        + project.getOpeningDate() + ","
                        + project.getClosingDate() + ","
                        + project.getManager().getNric() + ","
                        + project.getAvailableOfficerSlots() + ","
                        + officers);
                System.out.println(project.getClosingDate());
            }
        } catch (IOException e) {
            System.out.println("Error writing to files");
        }
    }

    /**
     * writes Manager data into ManagerList.csv
     * @param allUsers list of all users
     */
    static void writeManager(AllUsers allUsers) {
        String fileName = "ManagerList.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Write header row
            writer.println("Name,NRIC,Age,Marital Status,Password");

            // Write data rows
            for (User user : allUsers.getUsers()) {
                if (user instanceof HDBManager) {
                    writer.println(user.getNric() + "," + user.getNric() + "," + user.getAge() + "," + user.getMaritalStatus() + "," + user.getPassword());
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to files");
        }
    }

    /**
     * writes Applicant data into ApplicantList.csv
     * @param allUsers list of all users
     */
    static void writeApplicant(AllUsers allUsers) {
        String fileName = "ApplicantList.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Write header row
            writer.println("Name,NRIC,Age,Marital Status,Password");

            // Write data rows
            for (User user : allUsers.getUsers()) {
                if (user instanceof Applicant) {
                    writer.println(user.getNric() + "," + user.getNric() + "," + user.getAge() + "," + user.getMaritalStatus() + "," + user.getPassword());
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to files");
        }
    }

    /**
     * writes Officer data into OfficerList.csv
     * @param allUsers list of all users
     */
    static void writeOfficer(AllUsers allUsers) {
        String fileName = "OfficerList.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Write header row
            writer.println("Name,NRIC,Age,Marital Status,Password");

            // Write data rows
            for (User user : allUsers.getUsers()) {
                if (user instanceof HDBOfficer) {
                    writer.println(user.getNric() + "," + user.getNric() + "," + user.getAge() + "," + user.getMaritalStatus() + "," + user.getPassword());
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to files");
        }
    }

    static void writeAllFiles(List<HDBProject> allProjects, AllUsers allUsers) {
        writeRegistration(allProjects);
        writeUnit(allProjects);
        writeApplications(allProjects);
        writeQuery(allProjects);
        writeWithdrawal(allProjects);
        writeProject(allProjects);
        writeManager(allUsers);
        writeApplicant(allUsers);
        writeOfficer(allUsers);
    }

}
