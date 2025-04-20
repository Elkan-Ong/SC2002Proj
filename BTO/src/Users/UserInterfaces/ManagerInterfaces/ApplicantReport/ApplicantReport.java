package Users.UserInterfaces.ManagerInterfaces.ApplicantReport;

import Misc.ApplicantReportFilter;
import Misc.ReportGenerator;
import Project.HDBProject;
import Project.ProjectApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * The main interface for creating a report on the Applicants of the Projects a Manager has created
 * Includes creating a filter, filtering the Applicants, and generating the report
 */
public interface ApplicantReport extends ReportValidation {

    /**
     * Creates a filter for the Applicants and Projects the Manager is interested in viewing as w
     * @param allPastProjects all the projects a Manager has created
     * @return the filter created
     */
    default ApplicantReportFilter createReportFilter(List<HDBProject> allPastProjects) {
        ApplicantReportFilter filter = new ApplicantReportFilter();
        filter.createFilter(allPastProjects);
        return filter;
    }

    /**
     * Applies the filter to the list of Applications
     * @param filteredApplications list of filtered Applications (list of Applicants that pass the filter to be added to)
     * @param applicantFilter the filter containing the information the Manager wants
     * @param allPastProjects all the projects a Manager has created
     */
    default void filterApplicants(List<ProjectApplication> filteredApplications, ApplicantReportFilter applicantFilter, List<HDBProject> allPastProjects) {
        for (HDBProject project : allPastProjects) {
            for (ProjectApplication application : project.getAllProjectApplications()) {

                if (!passesFlatTypeFilter(application, applicantFilter) ||
                        !passesProjectNameFilter(application, applicantFilter) ||
                        !passesMaritalStatusFilter(application, applicantFilter) ||
                        !passesAgeFilter(application, applicantFilter)) {
                    continue;
                }

                filteredApplications.add(application);
            }
        }
    }

    /**
     * Calls all the respective methods to generate a report
     * @param allPastProjects all the projects a Manager has created
     */
    default void getApplicantReport(List<HDBProject> allPastProjects){
        List<ProjectApplication> filteredApplications = new ArrayList<>();
        ApplicantReportFilter applicantFilter = createReportFilter(allPastProjects);
        filterApplicants(filteredApplications, applicantFilter, allPastProjects);
        ReportGenerator.generateReport(filteredApplications, applicantFilter);
    }
}
