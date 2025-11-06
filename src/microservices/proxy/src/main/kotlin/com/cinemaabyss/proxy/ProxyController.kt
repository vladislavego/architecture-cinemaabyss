package com.cinemaabyss.proxy.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
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
    fun getMovies(): Mono<ResponseEntity<String>> {
        val routeToNew = Random.nextInt(100) < migrationPercent
        val client = if (routeToNew) moviesClient else legacyClient
        val target = if (routeToNew) "NEW" else "LEGACY"
        println("Routing GET $MOVIES_PATH to $target service (migrationPercent=$migrationPercent%)")

        return client.get().uri(MOVIES_PATH)
            .retrieve()
            .bodyToMono(String::class.java)
            .map { ResponseEntity.ok(it) }
    }

    @PostMapping("/movies")
    fun createMovie(@RequestBody body: String): Mono<ResponseEntity<String>> {
        println("Routing POST $MOVIES_PATH to LEGACY service")
        return legacyClient.post().uri(MOVIES_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .retrieve()
            .bodyToMono(String::class.java)
            .map { ResponseEntity.status(201).body(it) }
    }

    @GetMapping("/users")
    fun getUsers(): Mono<ResponseEntity<String>> {
        println("Routing GET $USERS_PATH to LEGACY service")
        return legacyClient.get().uri(USERS_PATH)
            .retrieve()
            .bodyToMono(String::class.java)
            .map { ResponseEntity.ok(it) }
    }

    @PostMapping("/users")
    fun createUser(@RequestBody body: String): Mono<ResponseEntity<String>> {
        println("Routing POST $USERS_PATH to LEGACY service")
        return legacyClient.post().uri(USERS_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .retrieve()
            .bodyToMono(String::class.java)
            .map { ResponseEntity.status(201).body(it) }
    }

    @GetMapping("/payments")
    fun getPayments(): Mono<ResponseEntity<String>> {
        println("Routing GET $PAYMENTS_PATH to LEGACY service")
        return legacyClient.get().uri(PAYMENTS_PATH)
            .retrieve()
            .bodyToMono(String::class.java)
            .map { ResponseEntity.ok(it) }
    }

    @PostMapping("/payments")
    fun createPayment(@RequestBody body: String): Mono<ResponseEntity<String>> {
        println("Routing POST $PAYMENTS_PATH to LEGACY service")
        return legacyClient.post().uri(PAYMENTS_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .retrieve()
            .bodyToMono(String::class.java)
            .map { ResponseEntity.status(201).body(it) }
    }

    @GetMapping("/subscriptions")
    fun getSubscriptions(): Mono<ResponseEntity<String>> {
        println("Routing GET $SUBSCRIPTIONS_PATH to LEGACY service")
        return legacyClient.get().uri(SUBSCRIPTIONS_PATH)
            .retrieve()
            .bodyToMono(String::class.java)
            .map { ResponseEntity.ok(it) }
    }

    @PostMapping("/subscriptions")
    fun createSubscription(@RequestBody body: String): Mono<ResponseEntity<String>> {
        println("Routing POST $SUBSCRIPTIONS_PATH to LEGACY service")
        return legacyClient.post().uri(SUBSCRIPTIONS_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .retrieve()
            .bodyToMono(String::class.java)
            .map { ResponseEntity.status(201).body(it) }
    }

    @PostMapping("/events/{type}")
    fun createEvent(@PathVariable type: String, @RequestBody body: String): Mono<ResponseEntity<String>> {
        println("Routing POST $EVENTS_PATH/$type to EVENTS service")
        return eventsClient.post()
            .uri("$EVENTS_PATH/$type")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .retrieve()
            .bodyToMono(String::class.java)
            .map { ResponseEntity.status(201).body(it) }
    }
}