package AppInterfaces;

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


public interface WriteFiles {
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
                            writer.println(project.getName() + "," + flat.getType() + "," + unit.getBookedBy().getName() + "," + unit.getUnitID());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                    writer.println(application.getApplicant().getName() + "," + project.getName() + "," + application.getSelectedType().getType() + "," + status);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void writeQuery(List<HDBProject> allProjects) {
        String fileName = "QueryList.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Write header row
            writer.println("title,query,reply,applicant,project,timestamp");

            // Write data rows
            for (HDBProject project : allProjects) {
                for (Query query : project.getQueries()) {
                    String reply = query.getReply() == null ? "" : query.getReply();
                    writer.println(query.getTitle() + "," + query.getQuery() + "," + reply + "," + query.getApplicant().getName() + "," + project.getName() + "," + query.getTimestamp());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                    writer.println(withdrawal.getApplicant().getName() + "," + withdrawal.getProjectApplication().getAppliedProject().getName() + "," + status);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String escapeCsv(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = value.replace("\"", "\"\""); // Escape double quotes
            return "\"" + value + "\"";         // Wrap in quotes
        }
        return value;
    }

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
                        officers = officers.concat(officer.getName());
                    } else {
                        officers = officers.concat("," + officer.getName());
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
                        + project.getManager().getName() + ","
                        + project.getAvailableOfficerSlots() + ","
                        + officers);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void writeManager(AllUsers allUsers) {
        String fileName = "ManagerList.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Write header row
            writer.println("Name,NRIC,Age,Marital Status,Password");

            // Write data rows
            for (User user : allUsers.getUsers()) {
                if (user instanceof HDBManager) {
                    writer.println(user.getName() + "," + user.getNric() + "," + user.getAge() + "," + user.getMaritalStatus() + "," + user.getPassword());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void writeApplicant(AllUsers allUsers) {
        String fileName = "ApplicantList.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Write header row
            writer.println("Name,NRIC,Age,Marital Status,Password");

            // Write data rows
            for (User user : allUsers.getUsers()) {
                if (user instanceof Applicant) {
                    writer.println(user.getName() + "," + user.getNric() + "," + user.getAge() + "," + user.getMaritalStatus() + "," + user.getPassword());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void writeOfficer(AllUsers allUsers) {
        String fileName = "OfficerList.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            // Write header row
            writer.println("Name,NRIC,Age,Marital Status,Password");

            // Write data rows
            for (User user : allUsers.getUsers()) {
                if (user instanceof HDBOfficer) {
                    writer.println(user.getName() + "," + user.getNric() + "," + user.getAge() + "," + user.getMaritalStatus() + "," + user.getPassword());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
