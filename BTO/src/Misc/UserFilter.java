package Misc;

public class UserFilter {
    // Can add more variables to filter
    // Ordering by ???
    // also could potentially put all filtered projects here for better runtime
    private String neighbourhood = null;
    private String type = null;

    public UserFilter(String neighbourhood, String type) {
        this.neighbourhood = neighbourhood;
        this.type = type;
    }

}
