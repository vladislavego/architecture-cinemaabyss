package com.cinemaabyss.proxy.controller

import org.springframework.web.bind.annotation.*

@RestController
class HealthController {
    @GetMapping("/health")
    fun health() = mapOf("status" to true)
}