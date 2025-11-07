package com.cinemaabyss.proxy.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient

@RestController
@RequestMapping
class HealthController(
    private val moviesClient: WebClient,
    private val legacyClient: WebClient,
    private val eventsClient: WebClient
) {
    @GetMapping("/health")
    fun rootHealth(): Map<String, Boolean> =
        mapOf("status" to true)

    @GetMapping("/api/movies/health")
    fun moviesHealth(): ResponseEntity<String> {
        val response = moviesClient.get()
            .uri("/api/movies/health")
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/api/events/health")
    fun eventsHealth(): ResponseEntity<String> {
        val response = eventsClient.get()
            .uri("/api/events/health")
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
        return ResponseEntity.ok(response)
    }

    @GetMapping("/api/users/health")
    fun usersHealth(): ResponseEntity<String> {
        val response = legacyClient.get()
            .uri("/health")
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
        return ResponseEntity.ok(response)
    }
}