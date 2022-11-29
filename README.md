# sic-code-api

## Service summary

The `sic-code-api` is an Spring Boot API for searching SIC Codes (industrial classification of a company) which are stored on a Mongo database. The search uses MongoDB full-text search capabilities and can be used to search for a particular SIC Code or text contained in either the SIC Code description or Economic activity. The results are returned in order of relevance determined by the full text-search. In addition there is a filter to only return exact phrase matches.

The data load is in  [Github `sic-code-data` repository](https://github.com/companieshouse/sic-code-data).

Within Spring Boot, Spring Data Mongo is used to simplify data access to the required Mongo collection (`combined_sic_activities`).

This has a Rest API with the following endpoints:

URL                                    | VERB | Notes
-------------------------------------- | ---- | --------------------------------------------------------------------------
`/internal/sic-code-search`            | POST | This uses a text search to search for SIC Codes (with an option to do an "match phrase"). See [Full-text search](docs/full-text-search.md) for more details of this search.
`/internal/sic-code-search/heathcheck` | GET  | This endpoint just returns a 200 Status code and "sic-code-api is alive" response

See the [OpenAPI Specification](spec/api-spec.json) which defines the HTTP responses and JSON returns for all methods in the API.

The data rarely changes and mainly comes from external sources and from the application point of view is "read only". The Mongo import files are kept within this Git Repository.

## Guides

- [Download and run application locally](docs/download-and-run-locally.md).
- [Use within `chs-docker-development`](docs/use-with-chs-docker-development.md).
- [Environmental variables used](docs/environmental-variables.md).
- [The Sic Code database](docs/sic-code-database.md).
- [Full-text search](docs/full-text-search.md)

## Deployment Pipeline

This application is deployed to the team and development Meso Marathon environments using concourse and after deploying automated tests using karate are run. Since the database is read only then the concourse task for refreshing this is manual.

See `https://ci.platform.aws.chdev.org/teams/team-pa/pipelines/sic-code-api` for the pipeline in action.

## Release to staging and live

This follows the standard process for Meso Marathon applications and is manually released in staging and live with instructions provided in a release note.
