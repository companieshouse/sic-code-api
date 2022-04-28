# Use with `docker-chs-development`

- Clone Docker CHS Development and follow the steps in the README.
- Run `./bin/chs-dev modules enable sic-code`
- Run `./bin/chs-dev development enable sic-code` (Not necessary step. Only run this if you want to activate dev mode to make changes).
- Run docker using `tilt up` in the docker-chs-development directory.
- Use spacebar in the command line to open tilt window - wait for sic-code-api to become green.
- If you are using the api directly, then use this url: `http://chs.local/sic-code-api`


## Curl

``` bash
# Any word
curl -H "Authorization: Basic <AUTH_TOKEN>"  -w '%{http_code}' --header "Content-Type: application/json" \
  --request POST \
  --data '{"search_string": "Barley growing", "match_phrase": 'false', "context_id": "sic-code-web-155982514859810330"}' \
  http://api.chs.local:4001/internal/sic-code-search

# Exact match
curl -H "Authorization: Basic <AUTH_TOKEN>"   -w '%{http_code}' --header "Content-Type: application/json" \
  --request POST \
  --data '{"search_string": "Barley growing", "match_phrase": 'true', "context_id": "sic-code-web-155982514859810330"}' \
  http://api.chs.local:4001/internal/sic-code-search

  # HealthCheck
curl -H "Authorization: Basic <AUTH_TOKEN>" -w '%{http_code}' --header "Content-Type: application/json" \
  --request GET \
  http://api.chs.local:4001/internal/sic-code-search/healthcheck
```

## Postman

Set your postman to use basic Auth and correct content tyope:

- Click the `Authorization` tab on your request.
- Select `Basic Auth` from the `TYPE` dropdown box.
- Enter the <CHS_INTERNAL_API_KEY>(This can be found in the docker-chs-development repo) into the `Username` field.
- This should generate an `Authorization` header in the `Headers` tab.

### Any word

- Set request to `POST`.
- Enter URL: `http://api.chs.local:4001/internal/sic-code-search`
- Select `Body` tab.
- Toggle `Raw` button.
- Select `JSON` from dropdown.
- Enter JSON into Body:

```json

 {"search_string": "Barley Growing", 
  "match_phrase": false,
  "context_id": "1111"}

```

- Click `Send` button.

### Exact Match

- Set request to `POST`.
- Enter URL: `http://api.chs.local:4001/internal/sic-code-search`
- Select `Body` tab.
- Toggle `Raw` button.
- Select `JSON` from dropdown.
- Enter JSON into Body:

```json

 {"search_string": "Barley Growing", 
  "match_phrase": true,
  "context_id": "1111"}

```

- Click `Send` button.

### HealthCheck

- Set request to `GET`.
- Enter URL: `http://api.chs.local:4001/internal/sic-code-search/healthcheck`
- Click `Send` button.
