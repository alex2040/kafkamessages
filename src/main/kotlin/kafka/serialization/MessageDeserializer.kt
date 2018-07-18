package kafka.serialization

import com.google.protobuf.InvalidProtocolBufferException
import kafka.MessageProto.Message
import org.apache.kafka.common.serialization.Deserializer

class MessageDeserializer: Deserializer<Message> {
    override fun configure(configs: MutableMap<String, *>?, isKey: Boolean) {
    }

    override fun close() {
    }

    override fun deserialize(topic: String, data: ByteArray): Message {
        try {
            return Message.parseFrom(data)
        } catch (e: InvalidProtocolBufferException) {
            throw RuntimeException("Failed to parse message " + e.message, e)
        }
    }
}