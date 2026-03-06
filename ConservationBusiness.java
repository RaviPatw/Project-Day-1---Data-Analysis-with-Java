public class ConservationBusiness {
    public static final String DEFAULT_BUSINESS_NAME = "Beginner Bird Tracker";
    public static int businessProfilesCreated = 0;

    private String businessName;
    private String analystName;

    // Preconditions: none.
    // Postconditions: object uses default values.
    public ConservationBusiness() {
        this.businessName = DEFAULT_BUSINESS_NAME;
        this.analystName = "Student Analyst";
        businessProfilesCreated++;
    }

    // Preconditions: businessName and analystName are provided by user.
    // Postconditions: object stores custom values.
    public ConservationBusiness(String businessName, String analystName) {
        this.businessName = businessName;
        this.analystName = analystName;
        businessProfilesCreated++;
    }

    public String getSummary() {
        return "Business: " + businessName + " | Analyst: " + analystName;
    }
}
