import kafka.Consumer
import kafka.Producer
import javax.ws.rs.client.ClientBuilder

private val producer = Producer()

fun main(args: Array<String>) {
    val consumer = Consumer()
    while (true) {
        val records = consumer.receiveMessages()
        for (record in records) {
            System.out.println("message : ${record.value().body}")
            sendTelegramMessage(record.value().body)
        }
    }
}

private fun sendTelegramMessage(msg: String) {
    val client = ClientBuilder.newClient()
    val response = client.target("https://api.telegram.org")
            .path("bot${Configuration.getBotToken()}/sendMessage")
            .queryParam("chat_id", Configuration.getChatId())
            .queryParam("text", msg)
            .request().get()

    if (response.status != 200) {
        putMessageToKafka(msg)
    }
}

private fun putMessageToKafka(msg: String) {
    producer.sendMessage(msg)
}