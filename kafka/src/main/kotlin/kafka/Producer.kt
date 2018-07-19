package kafka

import kafka.MessageProto.Message
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.*
import java.util.concurrent.ExecutionException

class Producer {

    private val producer: KafkaProducer<Int, Message>

    init {
        val properties = getProperties()
        println("create producer using kafka server: ${properties["bootstrap.servers"]}")
        producer = KafkaProducer(properties)
    }

    fun sendMessage(msg: String) {
        val message = Message.newBuilder().setBody(msg).build()
        try {
            producer.send(ProducerRecord("test", message))
            println("sent message to kafka: $message")
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }
    }

    fun close() {
        producer.close()
    }

    private fun getProperties(): Properties {
        val inputStream = javaClass.classLoader.getResourceAsStream("producer.properties")
        val properties = Properties()
        properties.load(inputStream)
        val kafkaServer = System.getenv("KAFKA_CONNECT")
        if (kafkaServer != null) {
            properties["bootstrap.servers"] = kafkaServer
        }
        return properties
    }
}