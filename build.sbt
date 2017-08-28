name := "TwitterProject"

version := "1.0"

scalaVersion := "2.12.1"
libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.11.0.0"
libraryDependencies += "org.twitter4j" % "twitter4j-core" % "4.0.6"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies +=  "com.datastax.cassandra" % "cassandra-driver-extras" % "3.0.0"
libraryDependencies += "com.typesafe" %  "config" % "1.3.1"


        