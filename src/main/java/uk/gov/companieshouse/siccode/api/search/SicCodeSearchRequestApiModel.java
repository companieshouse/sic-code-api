package uk.gov.companieshouse.siccode.api.search;

public class SicCodeSearchRequestApiModel {

    private String searchString;
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
