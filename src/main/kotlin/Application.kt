import kafka.Consumer
import kafka.Producer
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.MediaType

private val client = ClientBuilder.newClient()
private val producer = Producer()

fun main(args: Array<String>) {
    val consumer = Consumer()
    while (true) {
        val records = consumer.receiveMessages()
        for (record in records) {
            sendTelegramMessage(record.value().body)
            System.out.println("message : ${record.value().body}")
        }
    }
}

private fun sendTelegramMessage(msg: String) {
    val response = client.target("https://api.telegram.org")
            .path("bot${Configuration.getBotToken()}/sendMessage")
            .queryParam("chat_id", Configuration.getChatId())
            .queryParam("text", msg)
            .request(MediaType.TEXT_PLAIN_TYPE).get()

    if (response.status != 200) {
        putMessageToKafka(msg)
    }
}

private fun putMessageToKafka(msg: String) {
    producer.sendMessage(msg)
}