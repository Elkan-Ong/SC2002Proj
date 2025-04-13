package Misc;

import Project.ProjectApplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generates a report for Manager based on filters to display project Applicant information
 * Report will contain Project name, the flat types filtered, marital status of applicants filtered, average age of applicants
 */
public class ReportGenerator {
    /**
     * Displays the report after applying the filter on the list of applicants
     * @param applications all Applications for the Project
     * @param filter ApplicantReportFilter object which has all the filters the Manager selected
     */
    public static void generateReport(List<ProjectApplication> applications, ApplicantReportFilter filter) {
        // we will use a hash map to store the data
        // the key will be the project name, and it will have a value of a hash map with key value of Flat Type and a ProjectReport Object
        Map<String, Map<String, ProjectReport>> reportData = new HashMap<>();

        // Go through applications and populate the map accordingly
        for (ProjectApplication app : applications) {
            // Apply filters
            if (app.getApplicant().getBookedUnit() == null) continue;
            if (!filter.getFilteredProjectNames().contains(app.getAppliedProject().getName())) continue;
            if (!filter.getFilteredFlatTypes().contains(app.getSelectedType().getType())) continue;
            if (!filter.getFilteredMaritalStatus().contains(app.getApplicant().getMaritalStatus())) continue;
            if (app.getApplicant().getAge() < filter.getMinAge() || app.getApplicant().getAge() > filter.getMaxAge()) continue;

            // Group by project and flat type
            reportData
                    .computeIfAbsent(app.getAppliedProject().getName(), k -> new HashMap<>())
                    .computeIfAbsent(app.getSelectedType().getType(), k -> new ProjectReport())
                    .addApplicant(app.getApplicant().getMaritalStatus(), app.getApplicant().getAge());
        }

        // Output the report
        for (String project : reportData.keySet()) {
            // project name
            System.out.println("Project: " + project);
            // get the statistics for each flat
            Map<String, ProjectReport> flatStats = reportData.get(project);
            // iterate through the selected flat types
            for (String flatType : flatStats.keySet()) {
                // get the value of the
                ProjectReport stats = flatStats.get(flatType);
                System.out.printf("  Flat Type: %s\n    Total: %d\n    Single: %d\n    Married: %d\n    Avg Age: %.2f\n",
                        flatType, stats.getTotalApplicants(), stats.getSingleCount(), stats.getMarriedCount(), stats.getAverageAge());
            }
        }
    }
}
