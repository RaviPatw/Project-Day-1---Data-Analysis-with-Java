public class ConservationBusiness {
    public static final String DEFAULT_BUSINESS_NAME = "Beginner Bird Tracker";
    public static int businessProfilesCreated = 0;

    private String businessName;
    private String analystName;

    public ConservationBusiness() {
        this.businessName = DEFAULT_BUSINESS_NAME;
        this.analystName = "Student Analyst";
        businessProfilesCreated++;
    }

    public ConservationBusiness(String businessName, String analystName) {
        this.businessName = businessName;
        this.analystName = analystName;
        businessProfilesCreated++;
    }

    public String getSummary() {
        return "Business: " + businessName + " | Analyst: " + analystName;
    }
}
