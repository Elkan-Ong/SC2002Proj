package Misc;

/**
 * Contains information about a project such as the number of applicants, the number of single and married applicants
 */
public class ProjectReport {
    /**
     * Total number of Applicants
     */
    private int totalApplicants = 0;

    /**
     * Total number of single Applicants
     */
    private int singleCount = 0;

    /**
     * Total number of married Applicants
     */
    private int marriedCount = 0;

    /**
     * Total age of Applicants
     * to be used to calculate average age
     */
    private int totalAge = 0;

    /**
     * Gets total number of Applicants
     * @return total number of Applicants
     */
    public int getTotalApplicants() { return totalApplicants; }

    /**
     * Gets total number of single Applicants
     * @return total number of single Applicants
     */
    public int getSingleCount() { return singleCount; }

    /**
     * Gets total number of married Applicants
     * @return total number of married Applicants
     */
    public int getMarriedCount() { return marriedCount; }

    /**
     * Gets total age of Applicants
     * @return total age of Applicants
     */
    public int getTotalAge() { return totalAge; }

    /**
     * Updates counters based on the marital status and age of an Applicant for a project
     * @param maritalStatus marital status of Applicant
     * @param age age of Applicant
     */
    void addApplicant(String maritalStatus, int age) {
        totalApplicants++;
        totalAge += age;
        if ("Single".equalsIgnoreCase(maritalStatus)) {
            singleCount++;
        } else if ("Married".equalsIgnoreCase(maritalStatus)) {
            marriedCount++;
        }
    }

    /**
     * Calculates the average age of applicants
     * @return average age of applicants
     */
    double getAverageAge() {
        return totalApplicants == 0 ? 0 : (double) totalAge / totalApplicants;
    }
}
