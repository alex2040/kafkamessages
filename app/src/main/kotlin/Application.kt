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

    val url = "http://api.telegram.org/bot${Configuration.getBotToken()}/sendMessage?chat_id=${Configuration.getChatId()}&text=$message"
    println("url: $url")
    val request = Request.Builder()
            .url(url)
            .get()
            .build()
    try {
        val response = client.newCall(request).execute()
        if (response.code() != 200) {
            println("status code: ${response.code()}, gonna put the message to kafka again")
            putMessageToKafka(message)
        }
        if (response.body() != null) {
            response.close()
        }
    } catch (e: IOException) {
        e.printStackTrace()
        println("exception occurs, gonna put the message to kafka again")
        putMessageToKafka(message)
    } catch (e: RuntimeException) {
        e.printStackTrace()
    }
}

private fun putMessageToKafka(msg: String) {
    producer.sendMessage(msg)
}