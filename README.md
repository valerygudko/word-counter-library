# word-counter-library

Assumptions:

    1. English characters represent the valid alphabet for this exercise. Any other characters treated as non-alphabetic.
    2. External "Translator" class (included in a service package) has a trivial stub to represent the intended behavior for specified "translate" method described in the task.
    3. If input is null, empty or a blank word 400 error will be returned.
    4. If input word does not exist in the static dictionary of Translator respective error message with 404 error code will be returned.
    5. If input word has non-alphabetic characters error message with 422 error code will be returned.
    6. If input array of words contains invalid word (any of the type described in points 3-5) the input array of words won't be processed and respective error will be returned.
    
Re: further enhancements, to expose lib functionality to external clients 2 apis were created to represent 2 methods (API documentation could be added along with security in place). For resiliency Hazelcast has been used for distrubuted caching (further config ie TTL etc could be added).

## Get up and running

Run application using the following:

        ./gradlew clean bootRun

With the backend running you can access the application at: `http://localhost:8080`
