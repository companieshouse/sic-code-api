# Download and run locally (unix like machine)

## Prerequisites

In order to run the API locally, you'll need the following installed on your machine:

- [Java Open SDK 11](https://jdk.java.net/archive/)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)
- [MongoDB](https://www.mongodb.com) either locally or addressable locally (at least 4.2)

You also need to be on the CH Lan since this project uses jar files on a maven mirror that are part of CH private GitHub repositories.

## Instructions

1. Clone the application from [Github `sic-code-api` repository](https://github.com/companieshouse/sic-code-api),
2. Have a Mongo `sic_code` database available - this needs to point to a database that has already been created by the [Github `sic-code-data` repository](https://github.com/companieshouse/sic-code-data),
3. Set up the required [environmental variables](./environmental-variables.md)
4. Build the executable jar file (and run unit tests) using `make dev`
5. Run `./start.sh`

## Test application running locally

``` bash
# Any word
curl -H "ERIC-Identity: 123" -H "ERIC-Identity-Type: key" -H "ERIC-Authorised-Key-Roles:*"  -w '%{http_code}' --header "Content-Type: application/json" \
  --request POST \
  --data '{"search_string": "Barley growing", "match_phrase": 'false', "context_id": "sic-code-web-155982514859810330"}' \
  http://localhost:${SIC_CODE_API_PORT}/internal/sic-code-search

# Exact match
curl -H "ERIC-Identity: 123" -H "ERIC-Identity-Type: key" -H "ERIC-Authorised-Key-Roles:*"  -w '%{http_code}' --header "Content-Type: application/json" \
  --request POST \
  --data '{"search_string": "Barley growing", "match_phrase": 'true', "context_id": "sic-code-web-155982514859810330"}' \
  http://localhost:${SIC_CODE_API_PORT}/internal/sic-code-search

  # HealthCheck
curl -H "ERIC-Identity: 123" -H "ERIC-Identity-Type: key" -H "ERIC-Authorised-Key-Roles:*"  -w '%{http_code}' --header "Content-Type: application/json" \
  --request GET \
  http://localhost:${SIC_CODE_API_PORT}/internal/sic-code-search/healthcheck
```
