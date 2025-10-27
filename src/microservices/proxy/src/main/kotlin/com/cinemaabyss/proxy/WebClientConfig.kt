package com.cinemaabyss.proxy.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(
    @Value("\${MOVIES_SERVICE_URL}") private val moviesUrl: String,
    @Value("\${MONOLITH_URL}") private val legacyUrl: String
) {
    @Bean
    fun moviesClient(): WebClient = WebClient.builder().baseUrl(moviesUrl).build()

    @Bean
    fun legacyClient(): WebClient = WebClient.builder().baseUrl(legacyUrl).build()
}