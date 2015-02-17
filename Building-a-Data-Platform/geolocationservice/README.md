# geolocationservice

This provides a REST API for geolocation data.

The project can be easily built with Maven using `mvn install`.

# Prerequisites

- Kafka (available from `kafka.apache.org`)
- Java JDK 7+. 1.8.0_31 was used for this example.

# Configuration

- `src/main/resources/kafkaConfig.properties` - edit the metadata broker list to point to your Kafka cluster

# Usage

- Run the webservice: `java -jar target/geolocationservice-1.0-SNAPSHOT.jar`
- PUT data into the API with curl: `curl -X PUT http://localhost:8080/geoLocation/v1/setGeoLocation/{USERID}/{LATITUDE}/{LONGITUDE}/{TIMESTAMP}/{ACCURACY}`