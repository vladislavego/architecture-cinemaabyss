package com.cinemaabyss.events.service

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumerService {

    @KafkaListener(topics = ["movie-events"], groupId = "cinema-events-group")
    fun consume(message: String) {
        println("Received event: $message")
    }

    @KafkaListener(topics = ["user-events"], groupId = "cinema-events-group")
    fun consumeUserEvent(message: String) {
        println("Received user event: $message")
    }

    @KafkaListener(topics = ["payment-events"], groupId = "cinema-events-group")
    fun consumePaymentEvent(message: String) {
        println("Received payment event: $message")
    }
}