import Users.Applicant;

public class Query {
    private String query;
    private String reply;
    private Applicant applicant;

    public Query(Applicant applicant, String query) {
        this.applicant = applicant;
        this.query = query;
    }
}
