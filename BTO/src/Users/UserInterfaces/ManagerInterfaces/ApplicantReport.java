package Users.UserInterfaces.ManagerInterfaces;

import Misc.ApplicantReportFilter;
import Misc.ReportGenerator;
import Project.HDBProject;
import Project.ProjectApplication;

import java.util.ArrayList;

public interface ApplicantReport {

    default ApplicantReportFilter createReportFilter(ArrayList<HDBProject> allPastProjects) {
        ApplicantReportFilter filter = new ApplicantReportFilter();
        filter.createFilter(allPastProjects);
        return filter;
    }

    default void filterApplicants(ArrayList<ProjectApplication> filteredApplications, ApplicantReportFilter applicantFilter, ArrayList<HDBProject> allPastProjects) {
        boolean exit;
        for (HDBProject project : allPastProjects) {
            for (ProjectApplication application : project.getAllProjectApplications()) {
                exit = true;
                if (!applicantFilter.getFilteredFlatTypes().isEmpty()) {
                    for (String flatType : applicantFilter.getFilteredFlatTypes()) {
                        if (application.getSelectedType().getType().equals(flatType)) {
                            exit = false;
                            break;
                        }
                    }
                    if (exit) {
                        continue;
                    }
                }

                exit = true;
                if (!applicantFilter.getFilteredProjectNames().isEmpty()) {
                    for (String projectName : applicantFilter.getFilteredProjectNames()) {
                        if (application.getProjectName().equals(projectName)) {
                            exit = false;
                            break;
                        }
                    }
                    if (exit) {
                        continue;
                    }
                }


                exit = true;
                if (!applicantFilter.getFilteredMaritalStatus().isEmpty()) {
                    for (String maritalStatus : applicantFilter.getFilteredMaritalStatus()) {
                        if (application.getApplicant().getMaritalStatus().equals(maritalStatus)) {
                            exit = false;
                            break;
                        }
                    }
                    if (exit) {
                        continue;
                    }
                }


                int applicantAge = application.getApplicant().getAge();
                if (applicantAge > applicantFilter.getMaxAge() || applicantAge < applicantFilter.getMinAge()) {
                    continue;
                }

                filteredApplications.add(application);
            }
        }

    }

    default void getApplicantReport(ArrayList<HDBProject> allPastProjects){
        ArrayList<ProjectApplication> filteredApplications = new ArrayList<>();
        ApplicantReportFilter applicantFilter = createReportFilter(allPastProjects);
        filterApplicants(filteredApplications, applicantFilter, allPastProjects);
        ReportGenerator.generateReport(filteredApplications, applicantFilter);
    }
}
