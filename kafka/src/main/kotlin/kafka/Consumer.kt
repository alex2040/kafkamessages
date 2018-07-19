package kafka

import kafka.MessageProto.Message
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.*

class Consumer {

    private val consumer: KafkaConsumer<Int, Message>

    init {
        consumer = KafkaConsumer(getProperties())
        consumer.subscribe(Collections.singletonList("test"))
    }

    fun receiveMessages(): ConsumerRecords<Int, Message> {
        return consumer.poll(100)
    }

    private fun getProperties(): Properties {
        val inputStream = javaClass.classLoader.getResourceAsStream("consumer.properties")
        val properties = Properties()
        properties.load(inputStream)
        val kafkaServer = System.getenv("KAFKA_CONNECT")
        if (kafkaServer != null) {
            properties["bootstrap.servers"] = kafkaServer
        }
        println("consume using kafka server: ${properties["bootstrap.servers"]}")
        return properties
    }
}
