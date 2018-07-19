import kafka.Consumer
import kafka.Producer
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

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

private fun sendTelegramMessage(message: String) {
    val client = OkHttpClient()

    val request = Request.Builder()
            .url("https://api.telegram.org/bot${Configuration.getBotToken()}/sendMessage?chat_id=${Configuration.getChatId()}&text=$message")
            .get()
            .build()
    try {
        val response = client.newCall(request).execute()
        if (response.code() != 200) {
            putMessageToKafka(message)
        }
        if (response.body() != null) {
            response.close()
        }
    } catch (e: IOException) {
        putMessageToKafka(message)
    } catch (e: RuntimeException) {
        e.printStackTrace()
    }
}

private fun putMessageToKafka(msg: String) {
    producer.sendMessage(msg)
}