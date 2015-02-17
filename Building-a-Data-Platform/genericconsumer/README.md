# genericconsumer

Gets GPS logs from Kafka, and optionally parses & saves to HBase. The consumer assumes that a service (such as the geolocationservice package) is writing GPS logs to Kafka on the specified topic name.

The project can be easily built with Maven using `mvn install`.

# Prerequisites

- Kafka (available from `kafka.apache.org`)
- Java JDK 7+. 1.8.0_31 was used for this example.

# Usage

### Command-line Arguments

* `--zookeeper server1:port,server2:port` Zookeeper locations for Kafka
* `--groupid n` Group ID for Kafka consumer (should be unique to ensure all records are received)
* `--topicname topic` Kafka topic for GPS logs
* `--threads n` Number of Kafka consumer threads
* `--consumerclass classpath` Class of consumer to run, should be either `com.svds.genericconsumer.consumers.BasicConsumer` or `com.svds.genericconsumer.consumers.genericconsumer`
* `--parameters param1=val1,param2=val2` Optional parameters for the consumer. genericconsumer requires `zk=zookeeper-quorum-server,hbaseTable=tableName`

### Example

Run the basic consumer, which does not write to HBase:

    java -jar target/genericconsumer-1.0-SNAPSHOT-jar-with-dependencies.jar --zookeeper localhost:2181 --groupid 11 --topicname GeoLocation --threads 2 --consumerclass com.svds.genericconsumer.consumers.BasicConsumer


Run the GeoLocationConsumer, which writes GPS data to HBase:

    java -jar target/genericconsumer-1.0-SNAPSHOT-jar-with-dependencies.jar --zookeeper localhost:2181 --groupid 22 --topicname GeoLocation --threads 2 --consumerclass com.svds.genericconsumer.consumers.GeoLocationConsumer --parameters zk=zookeer-quorum-server,hbaseTable=gps-test