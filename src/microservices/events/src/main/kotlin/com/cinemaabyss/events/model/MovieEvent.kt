package com.cinemaabyss.events.model

import java.time.Instant

data class MovieEvent(
    val movieId: Long,
    val title: String,
    val action: String,
    val userId: Long,
    val timestamp: Instant = Instant.now()
)