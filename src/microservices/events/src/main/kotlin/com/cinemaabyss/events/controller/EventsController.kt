package com.cinemaabyss.events.controller

import com.cinemaabyss.events.dto.*
import com.cinemaabyss.events.service.EventMapper
import com.cinemaabyss.events.service.KafkaProducerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/events")
class EventsController(private val producer: KafkaProducerService) {

    @PostMapping("/movie")
    fun createMovieEvent(@RequestBody event: MovieEventRequest): ResponseEntity<Map<String, String>> {
        val movieEvent = EventMapper.toModel(event)
        producer.sendEvent("movie-events", movieEvent.toString())
        return ResponseEntity.status(201).body(mapOf("status" to "success"))
    }

    @PostMapping("/user")
    fun createUserEvent(@RequestBody event: UserEventRequest): ResponseEntity<Map<String, String>> {
        val userEvent = EventMapper.toModel(event)
        producer.sendEvent("user-events", userEvent.toString())
        return ResponseEntity.status(201).body(mapOf("status" to "success"))
    }

    @PostMapping("/payment")
    fun createPaymentEvent(@RequestBody event: PaymentEventRequest): ResponseEntity<Map<String, String>> {
        val paymentEvent = EventMapper.toModel(event)
        producer.sendEvent("payment-events", paymentEvent.toString())
        return ResponseEntity.status(201).body(mapOf("status" to "success"))
    }
}