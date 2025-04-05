package Misc;

import Project.HDBProject;
import Users.Applicant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Query {
    private String title;
    private String query;
    private String reply = null;
    private Applicant applicant;
    private HDBProject project;
    private Date timestamp;
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public Query(Applicant applicant, HDBProject project, String title, String query) {
        this.applicant = applicant;
        this.project = project;
        this.title = title;
        this.query = query;
        this.timestamp = new Date();
    }

    public Query(Applicant applicant, HDBProject project, String title, String query, String timestamp) throws ParseException {
        this.applicant = applicant;
        this.project = project;
        this.title = title;
        this.query = query;
        this.timestamp = format.parse(timestamp);
    }

    public Applicant getApplicant() { return applicant; }

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

    public Date getTimestamp() { return timestamp; }

    public void displayQuery() {
        System.out.println("Title: " + this.title);
        System.out.println("Query: " + this.query);
        System.out.print("Reply: ");
        System.out.println(Objects.requireNonNullElse(this.reply, "No reply made yet."));
        System.out.println("Created on: " + timestamp);
    }

}
