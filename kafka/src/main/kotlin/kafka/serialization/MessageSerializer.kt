package kafka.serialization

import kafka.MessageProto.Message
import org.apache.kafka.common.serialization.Serializer

class MessageSerializer : Serializer<Message> {
    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {
    }

    override fun close() {
    }

    override fun serialize(topic: String, data: Message): ByteArray {
        return data.toByteArray()
    }
}