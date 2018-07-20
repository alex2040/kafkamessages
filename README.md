KAFKA MESSAGE QUEUE
===================

algorithm:
1. telegram bot puts a message to Kafka
2. the application reads the message from Kafka
3. the application sends read message to telegram channel 

telegram bot: @KafkaMessagesBot,
telegram channel: KafkaMessagesChannel