package com.cinemaabyss.events.service

import com.cinemaabyss.events.dto.*
import com.cinemaabyss.events.model.*

object EventMapper {

    fun toModel(dto: MovieEventRequest) = MovieEvent(
        movieId = dto.movieId,
        title = dto.title,
        action = dto.action,
        userId = dto.userId
    )

    fun toModel(dto: UserEventRequest) = UserEvent(
        userId = dto.userId,
        username = dto.username,
        action = dto.action
    )

    fun toModel(dto: PaymentEventRequest) = PaymentEvent(
        paymentId = dto.paymentId,
        userId = dto.userId,
        amount = dto.amount,
        status = dto.status,
        methodType = dto.methodType
    )
}