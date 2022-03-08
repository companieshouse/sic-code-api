package uk.gov.companieshouse.siccode.api.search;

import org.springframework.data.mongodb.core.query.TextCriteria;

/**
 *  This creates a TextCriteria to use in a Mongo Text Search query with the "combined_sic_activities"."activity_description_search_field" field.
 *  This text search is case insensitive
 *   
 *  Note that a TextCriteria search will search against all fields in a document where the annotation @TextIndexed has been applied
 */
public class SicCodeSearchTextCriteria {

    private final String searchString;

    public SicCodeSearchTextCriteria(String searchString) {
        this.searchString = searchString.toLowerCase();
    }

    public TextCriteria getTextCriteriaMatchAny() {
        return TextCriteria.forDefaultLanguage().matchingAny(searchString);
    }

    public TextCriteria getTextCriteriaMatchPhrase() {
        return TextCriteria.forDefaultLanguage().matchingPhrase(searchString);
    }
    
}
