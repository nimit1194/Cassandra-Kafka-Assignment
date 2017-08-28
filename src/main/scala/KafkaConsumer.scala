import java.util.{Collections, Properties}

import scala.collection.JavaConverters._
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}

object KafkaConsumer extends App with CassandraProvider {

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")

  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

  props.put("group.id", "consumer-group-1")

  props.put("enable.auto.commit", "true")
  props.put("auto.commit.interval.ms", "1000")

  props.put("auto.offset.reset", "earliest")

  props.put("session.timeout.ms", "30000")

  val topic = "kafka-topic-kip"

  val consumer: KafkaConsumer[Nothing, String] = new KafkaConsumer[Nothing, String](props)

  consumer.subscribe(Collections.singletonList(topic))

  while (true) {

    val records: ConsumerRecords[Nothing, String] = consumer.poll(100)
    cassandraConn.execute(s"CREATE TABLE IF NOT EXISTS hashtag (userid timestamp PRIMARY KEY, tweethash text) ")
    for (record <- records.asScala) {
      println(record.value())
      cassandraConn.execute(
        s"INSERT INTO hashtag (userid, tweethash) VALUES (dateOf(now()),'${record.value()}')")


    }

  }
}
