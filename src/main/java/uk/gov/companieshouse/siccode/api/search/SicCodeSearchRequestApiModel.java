package uk.gov.companieshouse.siccode.api.search;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SicCodeSearchRequestApiModel {

    @JsonProperty("search_string")
    private String searchString;

    @JsonProperty("match_phrase")
    private boolean matchPhrase;

    public String getSearchString() {
        return searchString;
    }
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
    public boolean isMatchPhrase() {
        return matchPhrase;
    }
    public void setMatchPhrase(boolean matchPhrase) {
        this.matchPhrase = matchPhrase;
    }
    
}
