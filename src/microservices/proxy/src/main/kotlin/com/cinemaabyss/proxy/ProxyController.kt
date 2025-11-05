package com.cinemaabyss.proxy.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import kotlin.random.Random

@RestController
@RequestMapping("/api")
class ProxyController(
    private val moviesClient: WebClient,
    private val legacyClient: WebClient,
    private val eventsClient: WebClient,
    @Value("\${MOVIES_MIGRATION_PERCENT:0}") private val migrationPercent: Int
) {

    companion object {
        private const val MOVIES_PATH = "/api/movies"
        private const val USERS_PATH = "/api/users"
        private const val PAYMENTS_PATH = "/api/payments"
        private const val SUBSCRIPTIONS_PATH = "/api/subscriptions"
        private const val EVENTS_PATH = "/api/events"
    }

    @GetMapping("/movies")
    fun getMovies(): ResponseEntity<String> {
        val routeToNew = Random.nextInt(100) < migrationPercent
        val client = if (routeToNew) moviesClient else legacyClient
        val target = if (routeToNew) "NEW" else "LEGACY"
        println("Routing GET $MOVIES_PATH to $target service (migrationPercent=$migrationPercent%)")

        val response = client.get().uri(MOVIES_PATH)
            .retrieve().bodyToMono(String::class.java).block()

        return ResponseEntity.ok(response)
    }

    @PostMapping("/movies")
    fun createMovie(@RequestBody body: String): ResponseEntity<String> {
        println("Routing POST $MOVIES_PATH to LEGACY service")
        val response = legacyClient.post().uri(MOVIES_PATH)
            .bodyValue(body).retrieve().bodyToMono(String::class.java).block()

        return ResponseEntity.status(201).body(response)
    }

    @GetMapping("/users")
    fun getUsers(): ResponseEntity<String> {
        println("Routing GET $USERS_PATH to LEGACY service")
        val response = legacyClient.get().uri(USERS_PATH)
            .retrieve().bodyToMono(String::class.java).block()

        return ResponseEntity.ok(response)
    }

    @PostMapping("/users")
    fun createUser(@RequestBody body: String): ResponseEntity<String> {
        println("Routing POST $USERS_PATH to LEGACY service")
        val response = legacyClient.post().uri(USERS_PATH)
            .bodyValue(body).retrieve().bodyToMono(String::class.java).block()

        return ResponseEntity.status(201).body(response)
    }

    @GetMapping("/payments")
    fun getPayments(): ResponseEntity<String> {
        println("Routing GET $PAYMENTS_PATH to LEGACY service")
        val response = legacyClient.get().uri(PAYMENTS_PATH)
            .retrieve().bodyToMono(String::class.java).block()

        return ResponseEntity.ok(response)
    }

    @PostMapping("/payments")
    fun createPayment(@RequestBody body: String): ResponseEntity<String> {
        println("Routing POST $PAYMENTS_PATH to LEGACY service")
        val response = legacyClient.post().uri(PAYMENTS_PATH)
            .bodyValue(body).retrieve().bodyToMono(String::class.java).block()

        return ResponseEntity.status(201).body(response)
    }

    @GetMapping("/subscriptions")
    fun getSubscriptions(): ResponseEntity<String> {
        println("Routing GET $SUBSCRIPTIONS_PATH to LEGACY service")
        val response = legacyClient.get().uri(SUBSCRIPTIONS_PATH)
            .retrieve().bodyToMono(String::class.java).block()

        return ResponseEntity.ok(response)
    }

    @PostMapping("/subscriptions")
    fun createSubscription(@RequestBody body: String): ResponseEntity<String> {
        println("Routing POST $SUBSCRIPTIONS_PATH to LEGACY service")
        val response = legacyClient.post().uri(SUBSCRIPTIONS_PATH)
            .bodyValue(body).retrieve().bodyToMono(String::class.java).block()

        return ResponseEntity.status(201).body(response)
    }

    @PostMapping("/events/{type}")
    fun createEvent(@PathVariable type: String, @RequestBody body: String): ResponseEntity<String> {
        println("Routing POST $EVENTS_PATH/$type to EVENTS service")
        val response = eventsClient.post().uri("$EVENTS_PATH/$type")
            .bodyValue(body).retrieve().bodyToMono(String::class.java).block()

        return ResponseEntity.status(201).body(response)
    }
}