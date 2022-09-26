ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.11.12"

lazy val root = (project in file("."))
  .settings(
    name := "SparkStreamingConsumer"
  )

libraryDependencies ++=Seq(
  "org.apache.spark" %% "spark-streaming" % "2.4.8",
  "org.apache.spark" %% "spark-core" % "2.4.8",
  // https://mvnrepository.com/artifact/org.apache.kafka/kafka-clients
)
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.2.1"
// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming-kafka-0-10
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.4.8"