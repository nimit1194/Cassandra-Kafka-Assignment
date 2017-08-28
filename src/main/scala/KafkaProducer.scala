
import java.util.Properties
import org.slf4j.{Logger, LoggerFactory}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object KafkaProducer extends App with CassandraProvider {

  private val log: Logger = LoggerFactory.getLogger(this.getClass)
  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")
  props.put("client.id", "ScalaProducerExample")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  props.put("acks", "all")

  props.put("retries", "0")

  props.put("batch.size", "16384")

  props.put("linger.ms", "1")

  props.put("buffer.memory", "33554432")

  val producer: KafkaProducer[Nothing, String] = new KafkaProducer[Nothing, String](props)

  val topic = "kafka-topic-kip"
  println(s"Sending Records in Kafka Topic [$topic]")
  val obj = new TwitterAnalysis
  val res: Future[List[String]] = obj.tweetsForQuery("MIT")
  res.map { value =>
    value.map {
      tweet =>
        val record: ProducerRecord[Nothing, String] = new ProducerRecord(topic, tweet.toString)
        println(s"Hii")
        println(s"${record.value} ")
        cassandraConn.execute(
          s"INSERT INTO hashtag (userid, tweethash) VALUES (dateOf(now()),'${record.value()}')")

        producer.send(record)
    }
  }
  Thread.sleep(10000)

}


