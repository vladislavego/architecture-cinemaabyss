package com.cinemaabyss.events.model

import java.time.Instant

data class UserEvent(
    val userId: Long,
    val username: String,
    val action: String,
    val timestamp: Instant = Instant.now()
)