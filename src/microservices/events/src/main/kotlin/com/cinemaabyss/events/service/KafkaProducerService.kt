package com.cinemaabyss.events.service

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducerService(private val kafkaTemplate: KafkaTemplate<String, String>) {

    fun sendEvent(topic: String, message: String) {
        kafkaTemplate.send(topic, message)
        println("Sent event to $topic: $message")
    }
}