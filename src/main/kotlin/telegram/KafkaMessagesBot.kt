package telegram

import Configuration
import kafka.Producer
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.exceptions.TelegramApiException


class KafkaMessagesBot : TelegramLongPollingBot() {

    private val producer = Producer()

    override fun getBotUsername(): String {
        return "Bot"
    }

    override fun onUpdateReceived(e: Update) {
        val message = e.message
        producer.sendMessage(message.text)
    }

    override fun getBotToken(): String {
        return Configuration.getBotToken()
    }
}

fun main(args: Array<String>) {
    ApiContextInitializer.init()
    val telegramBotsApi = TelegramBotsApi()
    try {
        telegramBotsApi.registerBot(KafkaMessagesBot())
    } catch (e: TelegramApiException) {
        e.printStackTrace()
    }
}