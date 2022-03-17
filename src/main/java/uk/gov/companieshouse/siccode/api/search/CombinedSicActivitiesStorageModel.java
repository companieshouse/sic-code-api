package uk.gov.companieshouse.siccode.api.search;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.TextScore;

/**
 * CombinedSicActivitiesStorageModel is used in the MongoDB database collection
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

    @TextIndexed
    @Field("activity_description_search_field")
    private String activityDescriptionSearchField;

    @Field("sic_description")
    private String sicDescription;

    @Field("is_ch_activity")
    private boolean companiesHouseactivity;

    // This is required for sorting the results
    @TextScore
    Float score;

    public CombinedSicActivitiesStorageModel() {
    }

    public CombinedSicActivitiesStorageModel(String id, String sicCode, String activityDescription,
            String activityDescriptionSearchField, String sicDescription, boolean companiesHouseactivity) {
        this.id = id;
        this.sicCode = sicCode;
        this.activityDescription = activityDescription;
        this.activityDescriptionSearchField = activityDescriptionSearchField;
        this.sicDescription = sicDescription;
        this.companiesHouseactivity = companiesHouseactivity;
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

    public String getActivityDescriptionSearchField() {
        return activityDescriptionSearchField;
    }

    public void setActivityDescriptionSearchField(String activityDescriptionSearchField) {
        this.activityDescriptionSearchField = activityDescriptionSearchField;
    }

    public String getSicDescription() {
        return sicDescription;
    }

    public void setSicDescription(String sicDescription) {
        this.sicDescription = sicDescription;
    }

    public boolean isCompaniesHouseactivity() {
        return companiesHouseactivity;
    }

    public void setCompaniesHouseactivity(boolean companiesHouseactivity) {
        this.companiesHouseactivity = companiesHouseactivity;
    }

    /* Override both hashCode and equals for testing with Hamcrest matchers (hence
    /  want all data attributes)
    /  These methods were auto-generated
    */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((activityDescription == null) ? 0 : activityDescription.hashCode());
        result = prime * result + ((activityDescriptionSearchField == null) ? 0 : activityDescriptionSearchField.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + (companiesHouseactivity ? 1231 : 1237);
        result = prime * result + ((sicCode == null) ? 0 : sicCode.hashCode());
        result = prime * result + ((sicDescription == null) ? 0 : sicDescription.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CombinedSicActivitiesStorageModel other = (CombinedSicActivitiesStorageModel) obj;
        if (activityDescription == null) {
            if (other.activityDescription != null) {
                return false;
            }
        } else if (!activityDescription.equals(other.activityDescription)) {
            return false;
        }
        if (activityDescriptionSearchField == null) {
            if (other.activityDescriptionSearchField != null) {
                return false;
            }
        } else if (!activityDescriptionSearchField.equals(other.activityDescriptionSearchField)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (companiesHouseactivity != other.companiesHouseactivity) {
            return false;
        }
        if (sicCode == null) {
            if (other.sicCode != null) {
                return false;
            }
        } else if (!sicCode.equals(other.sicCode)) {
            return false;
        }
        if (sicDescription == null) {
            if (other.sicDescription != null) {
                return false;
            }
        } else if (!sicDescription.equals(other.sicDescription)) {
            return false;
        }
        return true;
    }

}
