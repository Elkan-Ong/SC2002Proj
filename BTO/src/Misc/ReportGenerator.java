package Misc;

import Project.ProjectApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// This class will be used to create reports for each project based on manager's filters and applications
public class ReportGenerator {
    public static void generateReport(ArrayList<ProjectApplication> applications, ApplicantReportFilter filter) {
        // we will use a hash map to store the data
        // the key will be the project name, and it will have a value of a hash map with key value of Flat Type and a ProjectReport Object
        Map<String, Map<String, ProjectReport>> reportData = new HashMap<>();

        // Go through applications and populate the map accordingly
        for (ProjectApplication app : applications) {
            // Apply filters
            if (!filter.getFilteredProjectNames().contains(app.getProjectName())) continue;
            if (!filter.getFilteredFlatTypes().contains(app.getSelectedType().getType())) continue;
            if (!filter.getFilteredMaritalStatus().contains(app.getApplicant().getMaritalStatus())) continue;
            if (app.getApplicant().getAge() < filter.getMinAge() || app.getApplicant().getAge() > filter.getMaxAge()) continue;

            // Group by project and flat type
            reportData
                    .computeIfAbsent(app.getProjectName(), k -> new HashMap<>())
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
