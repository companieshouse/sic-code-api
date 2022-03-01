package uk.gov.companieshouse.siccode.api.search;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 
    "_id" : ObjectId("6218f9a84c90c5ec91c8ff26"),
    "sic_code" : "10110",
    "activity_description" : "Abattoir (manufacture)",
    "activity_description_lower_case" : "abattoir (manufacture)",
    "sic_description" : "Processing and preserving of meat",
    "is_ch_activity" : false
 */
@Document(collection = "combined_sic_activities")
public class CombinedSicActivitiesStorageModel {

    @Id
    @Field("_id")
    private String id;

    @Field("sic_code")
    private String sicCode;   

    @Field("activity_description")
    private String activityDescription;   

    @Field("activity_description_lower_case")
    private String activityDescriptionLowerCase;   

    @Field("sic_description")
    private String sicDescription;   
    
    @Field("is_ch_activity")
    private boolean isCompaniesHouseactivity;

    
    public CombinedSicActivitiesStorageModel() {
    }

    public CombinedSicActivitiesStorageModel(String id, String sicCode, String activityDescription,
            String activityDescriptionLowerCase, String sicDescription, boolean isCompaniesHouseactivity) {
        this.id = id;
        this.sicCode = sicCode;
        this.activityDescription = activityDescription;
        this.activityDescriptionLowerCase = activityDescriptionLowerCase;
        this.sicDescription = sicDescription;
        this.isCompaniesHouseactivity = isCompaniesHouseactivity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSicCode() {
        return sicCode;
    }

    public void setSicCode(String sicCode) {
        this.sicCode = sicCode;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public String getActivityDescriptionLowerCase() {
        return activityDescriptionLowerCase;
    }

    public void setActivityDescriptionLowerCase(String activityDescriptionLowerCase) {
        this.activityDescriptionLowerCase = activityDescriptionLowerCase;
    }

    public String getSicDescription() {
        return sicDescription;
    }

    public void setSicDescription(String sicDescription) {
        this.sicDescription = sicDescription;
    }

    public boolean isCompaniesHouseactivity() {
        return isCompaniesHouseactivity;
    }

    public void setCompaniesHouseactivity(boolean isCompaniesHouseactivity) {
        this.isCompaniesHouseactivity = isCompaniesHouseactivity;
    }   
    
}
