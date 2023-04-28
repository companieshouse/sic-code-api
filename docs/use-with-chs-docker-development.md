# Use with `docker-chs-development`

This is part of the `sic-code` module.

Once this module is enabled, this project can be used within `docker-chs-development`

Note that the dataset is quite small. If you want the live dataset then you need to import it from the `combined_sic_activities` from the [Github `sic-code-data` repository](https://github.com/companieshouse/sic-code-data) project.

## Test URL

This returns data on the small data set within `docker-chs-development`.

``` bash
curl -H  "Authorization: ${CHS_DOCKER_INTERNAL_API_KEY}"  -w '%{http_code}' --header "Content-Type: application/json"  \
    --request POST   --data '{"search_string": "Barley growing", "match_phrase": 'false', "context_id": "sic-code-web-155982514859810330"}'  \
     http://api.chs.local:4001/internal/sic-code-search
```
