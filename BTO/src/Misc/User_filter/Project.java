public class Project {
    String projectName;
    String neighborhood;
    String type1;
    int type1Units;
    double type1Price;
    String type2;
    int type2Units;
    double type2Price;
    String applicationOpeningDate;
    String applicationClosingDate;
    String manager;
    int officerSlot;  
    String[] officers;  

    public Project(String projectName, String neighborhood, String type1, int type1Units, double type1Price, 
                   String type2, int type2Units, double type2Price, String applicationOpeningDate, 
                   String applicationClosingDate, String manager, int officerSlot, String officers) {
        this.projectName = projectName;
        this.neighborhood = neighborhood;
        this.type1 = type1;
        this.type1Units = type1Units;
        this.type1Price = type1Price;
        this.type2 = type2;
        this.type2Units = type2Units;
        this.type2Price = type2Price;
        this.applicationOpeningDate = applicationOpeningDate;
        this.applicationClosingDate = applicationClosingDate;
        this.manager = manager;
        this.officerSlot = officerSlot;
        this.officers = officers.split(",");  
    }

    
    public void display() {
        String officerNames = String.join(", ", officers); 
        System.out.println(projectName + " | " + neighborhood + " | " + type1 + ": " + type1Units + " units | Price: " + type1Price 
                            + " | " + type2 + ": " + type2Units + " units | Price: " + type2Price + " | Opening: " +applicationOpeningDate +
                            " | Closing: " + applicationClosingDate + " | Manager: " + manager + " | Officer Slots: " + officerSlot
                            + " | Officers: " + officerNames);
    }
}
