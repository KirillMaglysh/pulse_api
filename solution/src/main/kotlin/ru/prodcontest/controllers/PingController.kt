package ru.prodcontest.controllers

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/ping")
class PingController {
    private val logger = KotlinLogging.logger {}


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun ping() {
        logger.info { "Ping query received" }
    }
}
