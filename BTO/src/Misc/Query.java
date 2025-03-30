package Misc;

import Users.Applicant;

import java.util.Objects;

public class Query {
    private String title;
    private String query;
    private String reply = null;
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

    public void setReply(String reply) { this.reply = reply; }

    public void displayQuery() {
        System.out.println("Title: " + this.title);
        System.out.println("Query: " + this.query);
        System.out.print("Reply: ");
        System.out.println(Objects.requireNonNullElse(this.reply, "No reply made yet."));
    }

}
