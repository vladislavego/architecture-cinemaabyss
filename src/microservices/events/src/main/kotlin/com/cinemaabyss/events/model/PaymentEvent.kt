package com.cinemaabyss.events.model

import java.time.Instant

data class PaymentEvent(
    val paymentId: Long,
    val userId: Long,
    val amount: Double,
    val status: String,
    val methodType: String,
    val timestamp: Instant = Instant.now()
)