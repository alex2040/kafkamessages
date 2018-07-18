package kafka

import kafka.MessageProto.Message
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import java.util.*
import java.util.concurrent.ExecutionException

class Producer {

    private val producer: KafkaProducer<Int, Message>

    init {
        producer = KafkaProducer(getProperties())
    }

    fun sendMessage(msg: String) {
        val message = Message.newBuilder().setBody(msg).build()
        try {
            producer.send(ProducerRecord("test", message))
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
        return properties
    }
}