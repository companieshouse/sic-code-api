# Full Text Search

This application uses MongoDB full-text search to help user's find their SIC Codes. MongoDB full-text search is used to search string content in the `combined_sic_activities` collection (but just the fields that are part of the text index on this collection). The full-text search is case insensitive and results are returned in order of relevance. This is implemented within the application using Spring Mongo.

In the application, this search can be used to search for a particular SIC Code or text contained in either the SIC Code description or Economic activity. There are two input fields used in the full-text search:

- searchText: this is used to search the fields which are defined as part of the text index in the `combined_sic_activities` collection,
- matchPhrase: this boolean determines the type of full-text church used. This is either matching any word in the searchText (a word search) or an phrase match (exact match where the all the text entered in the searchText must be found )

For example, if we searched for "`Barley growing`":

- an exact match would only return "Barley Growing".
- and a word match could return values such as "Barley Growing", "Barley Farming", "Barley Malting", "Almond Growing", "Flower Growing", etc.

A further example, if we searched for "`Barley  growing`" (two spaces between Barley and growing):

- an exact match would not return any matches since there are two spaces between the words.
- and a word match could return values such as "Barley Growing", "Barley Farming", "Barley Malting", "Almond Growing", "Flower Growing", etc.

The results of this search are a list of [CombinedSicActivitiesApiModel](../src/main/java/uk/gov/companieshouse/siccode/api/search/CombinedSicActivitiesApiModel.java) objects sorted in order of most relevance first.

MongoDB provides text indexes to support text search queries on string content. Text indexes can include any field whose value is a string or an array of string elements. To perform text search queries, you must have a text index on your collection. The indexed fields are then scored by the number of matches in a field's value. So if we search a 3 word phrase and a field's value matches 2/3 words than that will be scored higher than a match of 1/3. The matched values are sorted in order of score and returned. Scoring can also be adjusted by using weights. You can assign a weight(number value) to a given field which acts as a multiplier to that fields match score. So if you want a field to have a higher importance compared to others then this can be used to facilitate that(this is not currently being used in this api).

See [CombinedSicActivitiesStorageModel](../src/main/java/uk/gov/companieshouse/siccode/api/search/CombinedSicActivitiesStorageModel.java) for our implementation which shows you which fields are searched on. The application is configured in the [application.properties](../src/main/resources/application.properties) file to automatically create the Text index using annotations on fields within the Java file.

## Advantages of a full-text search over a regular expression

- you can order the results in order of relevance (this would need to be implemented manually with a regular expression search),
- a full text search is case insensitive.

## References on Mongo full text search

- [Text Search on MongoDB Vendor docs](https://www.mongodb.com/docs/manual/text-search/). Atlas Text search staging/live, legacy text search used for self-managed deployments.
- [Digital Ocean Guide](https://www.digitalocean.com/community/tutorials/how-to-perform-full-text-search-in-mongodb)
- [Spring MongoDB docs on implementing Mongo full text search in a Spring application](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongodb.repositories.queries.full-text)
