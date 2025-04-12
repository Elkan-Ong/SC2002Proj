package Users.UserInterfaces.ManagerInterfaces.ApplicantReport;

import Misc.ApplicantReportFilter;
import Project.ProjectApplication;

/**
 * Methods that checks the filter conditions to the list of applications and returns a boolean to indicate them passing/failing the filter
 */
public interface ReportValidation {
    /**
     * checks if applicant applied for the flat type the Manager wants to get a report on
     * @param application Application submitted by an applicant
     * @param applicantFilter filter which contains the filtering information
     * @return whether the Application was for the corresponding flat type selected in the filter
     */
    default boolean passesFlatTypeFilter(ProjectApplication application, ApplicantReportFilter applicantFilter) {
        return applicantFilter.getFilteredFlatTypes().isEmpty() ||
                applicantFilter.getFilteredFlatTypes().contains(application.getSelectedType().getType());
    }

    /**
     * checks if applicant applied for the Project the Manager wants to get a report on
     * @param application Application submitted by an applicant
     * @param applicantFilter filter which contains the filtering information
     * @return whether the Application was for the corresponding Project selected in the filter
     */
    default boolean passesProjectNameFilter(ProjectApplication application, ApplicantReportFilter applicantFilter) {
        return applicantFilter.getFilteredProjectNames().isEmpty() ||
                applicantFilter.getFilteredProjectNames().contains(application.getAppliedProject().getName());
    }

    /**
     * checks if applicant's marital status is what the Manager wants to get a report on
     * @param application Application submitted by an applicant
     * @param applicantFilter filter which contains the filtering information
     * @return whether the Applicant has the corresponding marital status selected in the filter
     */
    default boolean passesMaritalStatusFilter(ProjectApplication application, ApplicantReportFilter applicantFilter ){
        return applicantFilter.getFilteredMaritalStatus().isEmpty() ||
                applicantFilter.getFilteredMaritalStatus().contains(application.getApplicant().getMaritalStatus());
    }

    /**
     * checks if applicant meets the age range the Manager wants to get a report on
     * @param application Application submitted by an applicant
     * @param applicantFilter filter which contains the filtering information
     * @return whether the is in the age range selected in the filter
     */
    default boolean passesAgeFilter(ProjectApplication application, ApplicantReportFilter applicantFilter) {
        int age = application.getApplicant().getAge();
        return age >= applicantFilter.getMinAge() && age <= applicantFilter.getMaxAge();
    }
}
