package com.cinemaabyss.events.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UserEventRequest(
    @JsonProperty("user_id") val userId: Long,
    val username: String,
    val action: String,
    val timestamp: String
)