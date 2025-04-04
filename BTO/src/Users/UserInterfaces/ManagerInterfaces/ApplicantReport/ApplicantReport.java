package Users.UserInterfaces.ManagerInterfaces.ApplicantReport;

import Misc.ApplicantReportFilter;
import Misc.ReportGenerator;
import Project.HDBProject;
import Project.ProjectApplication;

import java.util.ArrayList;
import java.util.List;

public interface ApplicantReport extends ReportValidation {

    default ApplicantReportFilter createReportFilter(List<HDBProject> allPastProjects) {
        ApplicantReportFilter filter = new ApplicantReportFilter();
        filter.createFilter(allPastProjects);
        return filter;
    }

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

    default void getApplicantReport(List<HDBProject> allPastProjects){
        List<ProjectApplication> filteredApplications = new ArrayList<>();
        ApplicantReportFilter applicantFilter = createReportFilter(allPastProjects);
        filterApplicants(filteredApplications, applicantFilter, allPastProjects);
        ReportGenerator.generateReport(filteredApplications, applicantFilter);
    }
}
