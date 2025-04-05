package Users.UserInterfaces.ManagerInterfaces.ApplicantReport;

import Misc.ApplicantReportFilter;
import Project.ProjectApplication;

public interface ReportValidation {
    default boolean passesFlatTypeFilter(ProjectApplication application, ApplicantReportFilter applicantFilter) {
        return applicantFilter.getFilteredFlatTypes().isEmpty() ||
                applicantFilter.getFilteredFlatTypes().contains(application.getSelectedType().getType());
    }

    default boolean passesProjectNameFilter(ProjectApplication application, ApplicantReportFilter applicantFilter) {
        return applicantFilter.getFilteredProjectNames().isEmpty() ||
                applicantFilter.getFilteredProjectNames().contains(application.getAppliedProject().getName());
    }

    default boolean passesMaritalStatusFilter(ProjectApplication application, ApplicantReportFilter applicantFilter ){
        return applicantFilter.getFilteredMaritalStatus().isEmpty() ||
                applicantFilter.getFilteredMaritalStatus().contains(application.getApplicant().getMaritalStatus());
    }

    default boolean passesAgeFilter(ProjectApplication application, ApplicantReportFilter applicantFilter) {
        int age = application.getApplicant().getAge();
        return age >= applicantFilter.getMinAge() && age <= applicantFilter.getMaxAge();
    }
}
