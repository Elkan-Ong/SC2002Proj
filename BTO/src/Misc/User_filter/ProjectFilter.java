import java.util.Scanner;

public class ProjectFilter {

    private Project[] projectList;
    private Applicant[] applicantList;
    private User user;
    
    private String neighborhoodFilter = "";
    private String flatTypeFilter = "";
    private String openingDateFilter = "";
    private String closingDateFilter = "";

    public ProjectFilter(Project[] projectList, Applicant[] applicantList, User user) {
        this.projectList = projectList;
        this.applicantList = applicantList;
        this.user = user;
    }

    public void showFilterMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Filter Menu ---");
            System.out.println("1. View all projects (alphabetical order)");
            System.out.println("2. Filter by neighborhood");
            System.out.println("3. Filter by flat type");
            System.out.println("4. Filter by application opening date");
            System.out.println("5. Filter by application closing date");
            System.out.println("6. Clear filters");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    display_sorted_by_name();
                    break;
                case 2:
                    System.out.print("Enter neighborhood to filter by: ");
                    String neighborhood = scanner.nextLine();
                    neighborhoodFilter = neighborhood;
                    filterProjectsByNeighborhood(neighborhood);
                    break;
                case 3:
                    System.out.print("Enter flat type to filter by (e.g., 2-Room, 3-Room): ");
                    String flatType = scanner.nextLine();
                    flatTypeFilter = flatType;
                    filterProjectsByFlatType(flatType);
                    break;
                case 4:
                    System.out.print("Enter opening date to filter by (e.g., 20-03-2025): ");
                    String openingDate = scanner.nextLine();
                    openingDateFilter = openingDate;
                    filterProjectsByOpeningDate(openingDate);
                    break;
                case 5:
                    System.out.print("Enter closing date to filter by (e.g., 20-03-2025): ");
                    String closingDate = scanner.nextLine();
                    closingDateFilter = closingDate;
                    filterProjectsByClosingDate(closingDate);
                    break;
                case 6:
                    clearFilters();
                    System.out.println("Filters cleared.");
                    break;
                case 7:
                    exit = true;
                    System.out.println("Exiting filter menu.");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }

        scanner.close();
    }

    private void display_sorted_by_name() {
        System.out.println("\n-------------------- Projects --------------------");
        for (int i = 0; i < projectList.length; i++) {
            for (int j = i + 1; j < projectList.length; j++) {
                if (projectList[i].projectName.compareTo(projectList[j].projectName) > 0) {
                    Project temp = projectList[i];
                    projectList[i] = projectList[j];
                    projectList[j] = temp;
                }
            }
        }

        for (Project project : projectList) {
            project.display();
        }
    }

    private void filterProjectsByNeighborhood(String neighborhood) {
        System.out.println("\n--- Projects filtered by neighborhood: " + neighborhood + " ---");
        boolean found = false;
        for (Project project : projectList) {
            if (project.neighborhood.equals(neighborhood)) {
                project.display();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No projects found in neighborhood: " + neighborhood);
        }
    }

    private void filterProjectsByFlatType(String flatType) {
        System.out.println("\n--- Projects filtered by flat type: " + flatType + " ---");
        boolean found = false;
        for (Project project : projectList) {
            if (project.type1.equals(flatType) || project.type2.equals(flatType)) {
                project.display();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No projects found with flat type: " + flatType);
        }
    }


    private void filterProjectsByOpeningDate(String openingDate) {
        System.out.println("\n--- Projects filtered by opening date: " + openingDate + " ---");
        boolean found = false;
        for (Project project : projectList) {
            if (project.applicationOpeningDate.equals(openingDate)) {
                project.display();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No projects found with opening date: " + openingDate);
        }
    }

    private void filterProjectsByClosingDate(String closingDate) {
        System.out.println("\n--- Projects filtered by closing date: " + closingDate + " ---");
        boolean found = false;
        for (Project project : projectList) {
            if (project.applicationClosingDate.equals(closingDate)) {
                project.display();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No projects found with closing date: " + closingDate);
        }
    }

    private void clearFilters() {
        neighborhoodFilter = "";
        flatTypeFilter = "";
        openingDateFilter = "";
        closingDateFilter = "";
    }
}
