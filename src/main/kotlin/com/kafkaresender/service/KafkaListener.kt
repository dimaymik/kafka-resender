package com.kafkaresender.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Service

@Service
class KafkaListener(private val senderService: SenderService) {

    private val log: Logger = LoggerFactory.getLogger(KafkaListener::class.java)

    @KafkaListener(topics = ["#{'\${spring.kafka.consumer.topic}'.split(',')}"], groupId = "\${group-id}")
    fun listen(kafkaMessage: String, @Header(KafkaHeaders.RECEIVED_TOPIC) topic: String) {
        log.trace("Message received: $kafkaMessage")
        process(kafkaMessage, topic)
    }

    private fun process(dto: String, topic: String) {
        senderService.send(dto, topic)
    }


}
