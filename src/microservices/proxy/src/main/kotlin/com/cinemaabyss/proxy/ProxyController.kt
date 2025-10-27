package com.cinemaabyss.proxy.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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

        println("Routing request to $target service (migrationPercent=$migrationPercent%)")

        val response = client.get()
            .uri("/api/movies")
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

        return ResponseEntity.ok(response)
    }

    @GetMapping("/users")
    fun getUsers(): ResponseEntity<String> {
        val client = legacyClient
        val target = "LEGACY"

        println("Routing request to $target service (migrationPercent=$migrationPercent%)")

        val response = client.get()
            .uri("/api/users")
            .retrieve()
            .bodyToMono(String::class.java)
            .block()

        return ResponseEntity.ok(response)
    }
}