package Misc;

import Misc.MiscInterfaces.QueryDisplay;
import Project.HDBProject;
import Users.Applicant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * A Query an Applicant submits when they want to enquire about a Project
 */
public class Query implements QueryDisplay {
    /**
     * title of the Query
     */
    private final String title;

    /**
     * query that the Applicant has about the project
     */
    private String query;

    /**
     * A reply given by an HDB Staff
     */
    private String reply = null;

    /**
     * Applicant that submitted the Query
     */
    private final Applicant applicant;

    /**
     * The Project Query was submitted enquiring about
     */
    private final HDBProject project;

    /**
     * The Date the Query was created on
     */
    private final Date timestamp;

    /**
     * Creates a new Query
     * Called from Applicant creating a new query
     * @param applicant Applicant who submitted the query
     * @param project The Project Applicant is enquiring about
     * @param title title of the Enquiry
     * @param query query the Applicant has on the Project
     */
    public Query(Applicant applicant, HDBProject project, String title, String query) {
        this.applicant = applicant;
        this.project = project;
        this.title = title;
        this.query = query;
        this.timestamp = new Date();
    }

    /**
     * Creates a new Query
     * Called from reading the QueryList csv file
     * @param applicant Applicant who submitted the query
     * @param project The Project Applicant is enquiring about
     * @param title title of the Enquiry
     * @param query query the Applicant has on the Project
     * @param timestamp The Date the Query was created on
     */
    public Query(Applicant applicant, HDBProject project, String title, String query, String timestamp) throws ParseException {
        this.applicant = applicant;
        this.project = project;
        this.title = title;
        this.query = query;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
        this.timestamp = format.parse(timestamp);
    }

    /**
     * Gets the Applicant who submitted the query
     * @return Applicant who submitted the query
     */
    public Applicant getApplicant() { return applicant; }

    /**
     * Gets the title of the query
     * @return title of the query
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the query
     * @return query Applicant has on the Project
     */
    public String getQuery() {
        return query;
    }

    /**
     * Changes the query
     * @param query new query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * Gets the reply made to the Applicant's query
     * @return reply made to the Applicant's query
     */
    public String getReply() {
        return reply;
    }

    /**
     * Changes the reply to the Applicant's query
     * @param reply reply to the applicant's query
     */
    public void setReply(String reply) { this.reply = reply; }

    /**
     * Gets the Date the Query was created on
     * @return The Date the Query was created on
     */
    public Date getTimestamp() { return timestamp; }

    /**
     * Gets the Project that a Query was submitted to
     * @return the Project that a Query was submitted to
     */
    public HDBProject getProject() {
        return project;
    }

    /**
     * Displays information about the Query
     */
    @Override
    public void displayQuery() {
        System.out.println("Title: " + this.title);
        System.out.println("Query: " + this.query);
        System.out.print("Reply: ");
        System.out.println(Objects.requireNonNullElse(this.reply, "No reply made yet."));
        System.out.println("Created on: " + timestamp);
    }
}
