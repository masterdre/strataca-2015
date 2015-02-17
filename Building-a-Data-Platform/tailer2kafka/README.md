# Description

This folder provides an example of tailing a log file into Kafka. Specifically, streaming Meetup logs are written to disk and copied by a Java tailer into Kafka.

The java project can be easily built with Maven using `mvn install`.

# Prerequisites

- Kafka (available from `kafka.apache.org`)
- Java JDK 7+. 1.8.0_31 was used for this example.

# Usage

- First run Kafka, from the Kafka install direcotry:
	- Start up a local single-note Zookeeper instance: `bin/zookeeper-server-start.sh config/zookeeper.properties`
	- Start Kafka: `bin/kafka-server-start.sh config/server.properties`
	- Create the Kafka topic (this is optional if topic autocreation is enabled in your Kafka config): `bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic meetupstream` 
- From the tailer2kafka directory, curl the meetup stream: `bin/run_curl_meetup_stream.sh`
- Run the Kafka tailer: `bin/run_tailer2kafka.sh`