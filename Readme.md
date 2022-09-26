# SparkConsumer
## BDT Poject part1
This project is designed by subject CS523 Big Data Project by MIU. <Br>
Current project is designed to integrate with kafka topic **wikimedia.recentupdate** <br>

to deploy the project first should run kafak cluster with topic **wikimedia.recentupdate**;

- Running kafka on local machine:
    - download [kafka_3.2.1](https://www.apache.org/dyn/closer.cgi?path=/kafka/3.2.1/kafka_2.13-3.2.1.tgz)
    - untar and run command `bin/zookeeper-server-start.sh config/zookeeper.properties`
    - run `bin/kafka-server-start.sh config/server.properties`
    - create topic to store events: `bin/kafka-topics.sh --create --topic wikimedia.recentupdate --bootstrap-server localhost:9092`

- Runn the application