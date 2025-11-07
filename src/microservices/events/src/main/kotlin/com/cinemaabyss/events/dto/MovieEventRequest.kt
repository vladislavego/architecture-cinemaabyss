package com.cinemaabyss.events.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class MovieEventRequest(
    @JsonProperty("movie_id") val movieId: Long,
    val title: String,
    val action: String,
    @JsonProperty("user_id") val userId: Long
)