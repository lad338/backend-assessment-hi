# Take home backend assessment

## TL;DR How to Run

- run `docker compose up`
- run `docker compose build` to rebuild if needed
- prerequisites:
  - `docker` 
  - Ports `8080` and `6379` available
- API is available on `http://localhost:8080/continents`
  - query parameter `countries` is available to be set
  - Example: `http://localhost:8080/continents?countries=CA,HK,US`
  - Header `Authorization` is used to demonstrate "authenticated users", setting it will be considered as authenticated users and leaving it will be considered as unauthenticated users.
  - cURL command example: `curl --header "Authorization: 123" http://localhost:8080/continents?countries=CA,HK,US`


## Assumptions
- It is assumed that the data from the GraphQL source will not be changing consistently. Therefore, data retrieved from the source will be parsed and cached for speeding up subsequent API calls
- Redis is being used as the cache. It is assumed that redis service will be available when starting the API service. Such that for simplicity, fallback plan when Redis is down is not implemented in the current solution.
- It is assumed that the source is always available and returning good response. Therefore, there is no extra handling on fail GraphQL calls or data validation checks in the current solution.
- It is assumed that the user input country codes will be generally good input. Therefore, there is no input validation currently implemented. Non-existing country codes are being ignored in current solution.
- It is assumed that the output order of countries (`countries` and `otherCountries`) should be based on the GraphQL source provided (such ordering is preserved in cache). In addition, order of user input shall not affect such ordering.
- It is assumed that the order of `continents` in the output required to be exact.

## Solution explanation
- When cache is not setup, GraphQL source will be called upon receiving an API call.
- The source from GraphQL will be parsed into Redis for caching.
- The cache has 2 parts: 
  - Mapping countryCode to its continentCode
  - Mapping continentCode to its name and full country list.
- By caching, time required to query the countries is reduced in terms of network delay on GraphQL calls and full list search.
- For rate limiting, either the authorization or the ip of the authenticated user or unauthenticated user is cached in Redis with a rate count.

## Others
- Overall test coverage is ~75% with 100% coverage on service layer.
- `package.json` is there because I used the node prettier with the Java plugin. I have also added some scripts so I can run them easier with `yarn` command.
- `/flushCache` API is available for demonstration purpose
  - cURL: `curl localhost:8080/flushCache`
- To demonstrate rate limiting, you may run the scripts `sh scripts/call5unauth.sh` and `sh scripts/call20auth.sh` for unauthenticated users and authenticated users respectively.
