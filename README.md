# Pet Tracking Application

## Assumptions

Since the task description allows for some interpretation, I’ve made the following assumptions:

- Pets and trackers are managed by the application and can be created and deleted
- Trackers periodically send update data  
  (I assume these would typically be GPS coordinates alongside other data, and then the backed service would determine
  if the tracker is within a designated zone. For simplicity, I have assumed that the tracker directly sends the two
  boolean values `inZone` and `lostTracker` instead.)

## Implementation comments:

- **Tracker entity**: Although not explicitly required, it makes sense to create a separate entity for trackers to avoid
  mixing tracker-specific properties, such as `trackerType`, into the pet entity. Since we are actually tracking the
  tracker itself and not the pet directly, I would even argue for the `inZone` property to belong to the tracker rather
  than the pet. However, as the task specifically states that `inZone` should be a property of the pet entity, I've
  followed that instruction.
- **Database**: By using Spring Data JPA without any database-specific native queries, the in-memory H2 database could
  easily be replaced with another persistent storage option, such as PostgreSQL for example.  
  Just for simplicity, the schema is automatically initialized by Hibernate. For a real application, I'd use a proper
  database migration tool like Flyway instead.
- **Pet and tracker creation**: To keep things simpler, I have not created a separate API to manage the trackers.
  Instead, whenever a pet is created, a new tracker is created for it automatically as well.

## Run

The application can be run via Gradle by:

```bash
./gradlew bootRun
```

## Tests

I've included a `data.sql` file that automatically inserts five pets and trackers on startup for testing purposes.  
Normally, this data would only be inserted for the tests.

Tests can be run by:

```bash
./gradlew test
```

## Example requests

The REST API is documented via OpenAPI.  
Once the application is running, Swagger UI is available at: `http://localhost:8080/api/v1/swagger-ui`

### Get existing Pets

```bash
curl --location 'localhost:8080/api/v1/pets'
```

### Create a new pet and tracker

```bash
curl --location 'localhost:8080/api/v1/pets' \
--header 'Content-Type: application/json' \
--data '{
    "petType": "CAT",
    "ownerId": 5,
    "trackerType": "CAT_SMALL"
}'
```

### Delete a pet

```bash
curl --location --request DELETE 'localhost:8080/api/v1/pets/d4d99907-c13e-41d3-be31-40b8742265ed'
```

### Update tracker data

```bash
curl --location --request PUT 'localhost:8080/api/v1/trackers/c8beb646-8f39-462b-841e-1993cdc1a957/data' \
--header 'Content-Type: application/json' \
--data '{
    "inZone": true,
    "lostTracker": true
}'
```

### Get the number of pets currently outside the zone grouped by pet type and tracker type

```bash
curl --location 'localhost:8080/api/v1/pets/outsideZone'
```
