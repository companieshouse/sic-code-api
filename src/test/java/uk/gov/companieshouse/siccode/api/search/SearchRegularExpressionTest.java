package uk.gov.companieshouse.siccode.api.search;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SearchRegularExpressionTest {

    @Test
    void oneStringOrMatch() {

        assertEquals("barley", new SearchRegularExpression("barley", false).getRegularExpression());
    }

    @Test
    void oneMixedCaseStringOrMatch() {

        assertEquals("barley", new SearchRegularExpression("BarleY", false).getRegularExpression());
    }

    @Test
    void threeStringOneSpaceOrMatch() {

        assertEquals("barley|farming|processing", new SearchRegularExpression("barley farming processing", false).getRegularExpression());
    }

    @Test
    void twoStringMultipleSpacesOrMatch() {

        assertEquals("barley|farming", new SearchRegularExpression("barley   farming", false).getRegularExpression());
    }

    @Test
    void oneStringAndMatch() {

        assertEquals("(?=.*barley)", new SearchRegularExpression("barley", true).getRegularExpression());
    }

    @Test
    void twoStringMultipleSpacesAndMatch() {

        assertEquals("(?=.*barley)(?=.*farming)",
                new SearchRegularExpression("barley   farming", true).getRegularExpression());
    }

    @Test
    void emptyString() {

        assertEquals("", new SearchRegularExpression("   ", false).getRegularExpression());
    }
}
