package uk.gov.companieshouse.siccode.api.search;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class CondensedSicActivitiesApiModel {

    @JsonProperty("sic_code")
    private String sicCode;

    @JsonProperty("sic_description")
    private String sicDescription;

    public CondensedSicActivitiesApiModel() {}

    public CondensedSicActivitiesApiModel(String sicCode, String sicDescription) {
        this.sicCode = sicCode;
        this.sicDescription = sicDescription;
    }

    public String getSicCode() {
        return sicCode;
    }

    public void setSicCode(String sicCode) {
        this.sicCode = sicCode;
    }

    public String getSicDescription() {
        return sicDescription;
    }

    public void setSicDescription(String sicDescription) {
        this.sicDescription = sicDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CondensedSicActivitiesApiModel that = (CondensedSicActivitiesApiModel) o;
        return Objects.equals(sicCode, that.sicCode) && Objects.equals(sicDescription, that.sicDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sicCode, sicDescription);
    }
}
