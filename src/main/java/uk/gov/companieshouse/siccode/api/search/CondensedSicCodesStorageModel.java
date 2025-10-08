package uk.gov.companieshouse.siccode.api.search;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@Document(collection = "condensed_sic_codes")
public class CondensedSicCodesStorageModel {

    @Id
    @Field("_id")
    private String id;

    @TextIndexed
    @Field("sic_code")
    private String sicCode;

    @Field("sic_description")
    private String sicDescription;

    public CondensedSicCodesStorageModel() {}

    public CondensedSicCodesStorageModel(String id, String sicCode, String sicDescription) {
        this.id = id;
        this.sicCode = sicCode;
        this.sicDescription = sicDescription;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

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
        CondensedSicCodesStorageModel that = (CondensedSicCodesStorageModel) o;
        return Objects.equals(id, that.id) && Objects.equals(sicCode, that.sicCode) &&
                Objects.equals(sicDescription, that.sicDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sicCode, sicDescription);
    }
}
