package telegram

import Configuration
import kafka.Producer
import org.apache.http.HttpHost
import org.apache.http.client.config.RequestConfig
import org.telegram.telegrambots.ApiContext
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.exceptions.TelegramApiException


class KafkaMessagesBot(options: DefaultBotOptions) : TelegramLongPollingBot(options) {

    private val producer = Producer()

    override fun getBotUsername(): String {
        return "Bot"
    }

    override fun onUpdateReceived(e: Update) {
        val message = e.message
        println("send message to kafka: $message")
        producer.sendMessage(message.text)
    }

    override fun getBotToken(): String {
        return Configuration.getBotToken()
    }
}

fun main(args: Array<String>) {
    ApiContextInitializer.init()
    val telegramBotsApi = TelegramBotsApi()
    val botOptions = getBotOptions()
    try {
        telegramBotsApi.registerBot(KafkaMessagesBot(botOptions))
    } catch (e: TelegramApiException) {
        e.printStackTrace()
    }
}

private fun getBotOptions(): DefaultBotOptions {
    val botOptions = ApiContext.getInstance(DefaultBotOptions::class.java)
    val httpHost = HttpHost(Configuration.getProxyHost(), Configuration.getProxyPort())
    val requestConfig = RequestConfig.custom().setProxy(httpHost).setAuthenticationEnabled(false).build()
    botOptions.requestConfig = requestConfig
    botOptions.httpProxy = httpHost
    return botOptions
}