package com.cinemaabyss.events.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class PaymentEventRequest(
    @JsonProperty("payment_id") val paymentId: Long,
    @JsonProperty("user_id") val userId: Long,
    val amount: Double,
    val status: String,
    val timestamp: String,
    @JsonProperty("method_type") val methodType: String
)