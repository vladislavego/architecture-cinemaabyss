package com.cinemaabyss.events.controller

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/events")
class HealthController {
    @GetMapping("/health")
    fun health() = mapOf("status" to true)
}