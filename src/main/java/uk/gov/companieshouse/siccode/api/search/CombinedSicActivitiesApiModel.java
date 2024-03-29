package uk.gov.companieshouse.siccode.api.search;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CombinedSicActivitiesApiModel is the data model that is sent from the
 * api to the client
 */
public class CombinedSicActivitiesApiModel {

    @JsonProperty("sic_code")
    private String sicCode;

    @JsonProperty("activity_description")
    private String activityDescription;

    @JsonProperty("sic_description")
    private String sicDescription;

    @JsonProperty("is_ch_activity")
    private boolean companiesHouseactivity;

    @JsonProperty("search_score")
    private Float score;

    public CombinedSicActivitiesApiModel() {
    }

    public CombinedSicActivitiesApiModel(String sicCode, String activityDescription,
            String sicDescription, boolean companiesHouseactivity) {
        this.sicCode = sicCode;
        this.activityDescription = activityDescription;
        this.sicDescription = sicDescription;
        this.companiesHouseactivity = companiesHouseactivity;
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

    public Float getScore() {
      return score;
    }

    public void setScore(Float score) {
      this.score = score;
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
        result = prime * result + (companiesHouseactivity ? 1231 : 1237);
        result = prime * result + ((sicCode == null) ? 0 : sicCode.hashCode());
        result = prime * result + ((sicDescription == null) ? 0 : sicDescription.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("java:S3776")  // IDE generated equals method
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
        CombinedSicActivitiesApiModel other = (CombinedSicActivitiesApiModel) obj;
        if (activityDescription == null) {
            if (other.activityDescription != null) {
                return false;
            }
        } else if (!activityDescription.equals(other.activityDescription)) {
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
