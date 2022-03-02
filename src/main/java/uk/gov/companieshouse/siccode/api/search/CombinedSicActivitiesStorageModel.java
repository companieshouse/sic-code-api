package uk.gov.companieshouse.siccode.api.search;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

    // Override both hashCode and equals for testing (hence want all attributes)
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((activityDescription == null) ? 0 : activityDescription.hashCode());
        result = prime * result
                + ((activityDescriptionLowerCase == null) ? 0 : activityDescriptionLowerCase.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + (isCompaniesHouseactivity ? 1231 : 1237);
        result = prime * result + ((sicCode == null) ? 0 : sicCode.hashCode());
        result = prime * result + ((sicDescription == null) ? 0 : sicDescription.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CombinedSicActivitiesStorageModel other = (CombinedSicActivitiesStorageModel) obj;
        if (activityDescription == null) {
            if (other.activityDescription != null)
                return false;
        } else if (!activityDescription.equals(other.activityDescription))
            return false;
        if (activityDescriptionLowerCase == null) {
            if (other.activityDescriptionLowerCase != null)
                return false;
        } else if (!activityDescriptionLowerCase.equals(other.activityDescriptionLowerCase))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (isCompaniesHouseactivity != other.isCompaniesHouseactivity)
            return false;
        if (sicCode == null) {
            if (other.sicCode != null)
                return false;
        } else if (!sicCode.equals(other.sicCode))
            return false;
        if (sicDescription == null) {
            if (other.sicDescription != null)
                return false;
        } else if (!sicDescription.equals(other.sicDescription))
            return false;
        return true;
    }    
    
}
