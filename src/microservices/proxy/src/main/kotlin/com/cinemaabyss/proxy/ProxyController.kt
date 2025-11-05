package com.cinemaabyss.proxy.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.WebClient
import kotlin.random.Random

@RestController
@RequestMapping("/api")
class MoviesProxyController(
    private val moviesClient: WebClient,
    private val legacyClient: WebClient,
    @Value("\${MOVIES_MIGRATION_PERCENT:0}") private val migrationPercent: Int
) {
    @GetMapping("/movies")
    fun getMovies(): ResponseEntity<String> {
        val routeToNew = Random.nextInt(100) < migrationPercent
        val client = if (routeToNew) moviesClient else legacyClient
        val target = if (routeToNew) "NEW" else "LEGACY"
        println("Routing GET /movies to $target service (migrationPercent=$migrationPercent%)")

        val response = client.get()
            .uri("/api/movies")
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

        return ResponseEntity.ok(response)
    }

    @PostMapping("/movies")
    fun createMovie(@RequestBody body: String): ResponseEntity<String> {
        println("Routing POST /movies to LEGACY service")
        val response = legacyClient.post()
            .uri("/api/movies")
            .bodyValue(body)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

        return ResponseEntity.status(201).body(response)
    }

    @GetMapping("/users")
    fun getUsers(): ResponseEntity<String> {
        println("Routing GET /users to LEGACY service")
        val response = legacyClient.get()
            .uri("/api/users")
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

        return ResponseEntity.ok(response)
    }

    @PostMapping("/users")
    fun createUser(@RequestBody body: String): ResponseEntity<String> {
        println("Routing POST /users to LEGACY service")
        val response = legacyClient.post()
            .uri("/api/users")
            .bodyValue(body)
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

        return ResponseEntity.status(201).body(response)
    }
}