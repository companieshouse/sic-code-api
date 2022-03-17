# sic-code-api

## Service summary

API for SIC Codes (industrial classification of a company) which uses a Mongo database of SIC codes and is implemented using Spring Boot.
Within Spring Boot, Spring Data Mongo is used to simplify data access to the required Mongo collection (`combined_sic_activities`).

This has a  Rest API with the following endpoints:

URL                       | VERB | Notes
------------------------- | ---- | --------------------------------------------------------------------------
`/internal/sic-code-search`        | POST | This does a keyword search to search for SIC Codes (with an option to search on one or all of the supplied words)

See the [api-spec](spec/api-spec.json) which defines the HTTP responses and JSON returns for all methods in the API.

The data is loaded into the database during the initial load as described in the [Data load README](DATALOAD-README.md). For the MVP version there will NOT be any updates to the Mongo Data (the SIC Code data is very stable with the last changes made in 2007, however going forward we will want to allow the addition of extra Economic activities for a SIC code which might result from people contacting Companies House about the nature of their proposed company).

## Requirements

In order to run the API locally, you'll need the following installed on your machine:

- [Java Open SDK 11](https://jdk.java.net/archive/)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)
- [MongoDB](https://www.mongodb.com) although this can be run within a Docker image.

## Getting Started

Have a Mongo database with the `sic_code` database. See [Data load README](DATALOAD-README.md).

1. Run `make clean build`
2. Run `./start.sh`

## Running in docker

This project uses the maven jib plugin to build docker images.

For internal Companies House development use, this docker build will be used within Docker CHS Development.

## Environment Variables

The following is a list of mandatory environment variables for the service to run:

Name                                 | Description                                                               | Example Value
------------------------------------ | ------------------------------------------------------------------------- | ------------------------
SIC_CODE_API_PORT                    | Application Port                                                          | 8080
SIC_CODE_API_MONGO_URL               | URL to MongoDB                                                            | `mongodb://mongo:27017`
SIC_CODE_API_DATABASE                | Name of Sic Code Mongo database                                           | sic_code

## Why a free text search was used rather than a regular expression find

The main advantage of a free text search is that you can order the results in order of relevance (this would need to be implemented manually with a regular expression file)

## Example Curl command for using the API

### Using localhost

``` bash
# Any word
curl -w '%{http_code}' --header "Content-Type: application/json" \
  --request POST \
  --data '{"search_string": "Barley growing", "match_phrase": 'false', "context_id": "sic-code-web-155982514859810330"}' \
  http://localhost:8080/internal/sic-code-search

# phrase match
curl -w '%{http_code}' --header "Content-Type: application/json" \
  --request POST \
  --data '{"search_string": "Barley growing", "match_phrase": 'true', "context_id": "sic-code-web-155982514859810330"}' \
  http://localhost:8080/internal/sic-code-search
```
