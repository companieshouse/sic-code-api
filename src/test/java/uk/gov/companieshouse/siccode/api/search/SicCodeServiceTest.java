package uk.gov.companieshouse.siccode.api.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.query.TextCriteria;

import uk.gov.companieshouse.siccode.api.groups.TestType;

@Tag(TestType.UNIT)
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_METHOD)
class SicCodeServiceTest {

    private static final String REQUEST_ID = "123";

    private final CombinedSicActivitiesStorageModel SEARCH_ROW = new CombinedSicActivitiesStorageModel("id", "sicCode", "activityDescription", "activityDescriptionSearchField", "sicDescription", true, LocalDateTime.of(2022, 1, 1, 0, 0, 0));

    @Mock
    private CombinedSicActivitiesRepository combinedSicActivitiesRepository;

    @InjectMocks
    private SicCodeService sicCodeService;

    @Test
    @DisplayName("Get empty list returned when the search string is empty")
    void searchNoResultsWithEmptySearchString() {

        var emptySearchRequest = new SicCodeSearchRequestApiModel();
        emptySearchRequest.setSearchString("  ");

        var results = sicCodeService.search(REQUEST_ID, emptySearchRequest);

        assertEquals(0, results.size());
    }

    @Test
    @DisplayName("Get expected results returned when the search string has a value and not a phase match")
    void searchAny() {

        var anySearchRequest = new SicCodeSearchRequestApiModel();
        anySearchRequest.setSearchString("Stubbed out search");
        anySearchRequest.setMatchPhrase(false);

        List<CombinedSicActivitiesStorageModel> expectedResults = new ArrayList<>();
        expectedResults.add(SEARCH_ROW);
        when(combinedSicActivitiesRepository.findAllByOrderByScore(any(TextCriteria.class))).thenReturn( expectedResults);

        var actualResults = sicCodeService.search(REQUEST_ID, anySearchRequest);

        assertEquals(1, actualResults.size());
        assertTrue(actualResults.contains(SEARCH_ROW));
    }

    @Test
    @DisplayName("Get expected results returned when the search string has a value and is a phase match")
    void searchPhrase() {

        var phraseSearchRequest = new SicCodeSearchRequestApiModel();
        phraseSearchRequest.setSearchString("Stubbed out search");
        phraseSearchRequest.setMatchPhrase(true);

        List<CombinedSicActivitiesStorageModel> expectedResults = new ArrayList<>();
        expectedResults.add(SEARCH_ROW);
        when(combinedSicActivitiesRepository.findAllByOrderByScore(any(TextCriteria.class))).thenReturn( expectedResults);

        var actualResults = sicCodeService.search(REQUEST_ID, phraseSearchRequest);

        assertEquals(1, actualResults.size());
        assertTrue(actualResults.contains(SEARCH_ROW));
    }
}
