package Misc;

public class ProjectReport {
    private int totalApplicants = 0;
    private int singleCount = 0;
    private int marriedCount = 0;
    private int totalAge = 0;

    public int getTotalApplicants() { return totalApplicants; }
    public int getSingleCount() { return singleCount; }
    public int getMarriedCount() { return marriedCount; }
    public int getTotalAge() { return totalAge; }

    void addApplicant(String maritalStatus, int age) {
        totalApplicants++;
        totalAge += age;
        if ("Single".equalsIgnoreCase(maritalStatus)) {
            singleCount++;
        } else if ("Married".equalsIgnoreCase(maritalStatus)) {
            marriedCount++;
        }
    }

    double getAverageAge() {
        return totalApplicants == 0 ? 0 : (double) totalAge / totalApplicants;
    }
}
