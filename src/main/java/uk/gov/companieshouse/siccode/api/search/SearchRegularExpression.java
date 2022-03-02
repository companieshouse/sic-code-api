package uk.gov.companieshouse.siccode.api.search;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

/**
 * SearchRegularExpression
 * 
 * This creates a regular expression to use in a Mongo Query with the "combined_sic_activities"."activity_description_lower_case" field
 * 
 * While it returns an empty string if the searchString itself is empty (includes null), it is not expected to be called in this case.
 */
public class SearchRegularExpression {

    private static final String REGEX_MULTIPLE_WHITESPACE_CHARACTERS = "\\s+";
    
    private final String searchString;
    private final boolean matchAllWords;

    public SearchRegularExpression(String searchString, boolean matchAllWords) {
        this.searchString = searchString;
        this.matchAllWords = matchAllWords;
    }
 
    public String getRegularExpression() {

        var regex = "";
        if (StringUtils.isEmpty(searchString)) {
            return regex;
        }

        var keywords = searchString.toLowerCase().split(REGEX_MULTIPLE_WHITESPACE_CHARACTERS);
        
        if (! matchAllWords) {
            return createMatchForAnyWord(keywords);
        }
        else {
            return createMatchForAllWords(keywords);       
        }
    }

    private String createMatchForAnyWord(String[] keywords) {
        return String.join("|", keywords);
    }

    /*
      This must create a non-consuming regular expression since we only want to match when all words are found.

      The required notation (which is passed to MongoDB) for each word is: (?=expr). 
      This means "match expr but after that continue matching at the original match-point.""

      You can do as many of these as you want, and this will create an "and" match. See Test class for examples

      Some StackOverFlow <a href="https://stackoverflow.com/questions/469913/regular-expressions-is-there-an-and-operator">stackoverflow discussion </a> 
    */
    private String createMatchForAllWords(String[] keywords) {
        String regex;
        regex = Arrays.stream(keywords)
                    .map( keyword -> "(?=.*" + keyword + ")" )
                    .collect(Collectors.joining());
        return regex;
    }
    
}