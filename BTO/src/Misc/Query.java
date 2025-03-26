package Misc;

import Users.Applicant;

public class Query {
    private String title;
    private String query;
    private String reply = "No reply yet!";
    private Applicant applicant;

    public Query(Applicant applicant, String title, String query) {
        this.applicant = applicant;
        this.title = title;
        this.query = query;
    }

    public String getTitle() {
        return title;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getReply() {
        return reply;
    }
}
