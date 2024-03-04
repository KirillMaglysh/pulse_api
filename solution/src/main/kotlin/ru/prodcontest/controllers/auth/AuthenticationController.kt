package ru.prodcontest.controllers.auth

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.prodcontest.requests.SignInRequest
import ru.prodcontest.services.AuthenticationService
import ru.prodcontest.services.UserService

@RestController
@RequestMapping("/api/auth/sign-in")
class AuthenticationController(
    @Autowired private val userService: UserService,
    @Autowired private val authService: AuthenticationService
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping
    @ResponseBody
    fun singIn(@RequestBody singInRequest: SignInRequest): ResponseEntity<out Any> {
        logger.info { "Trying to sign-in user: $singInRequest" }

        if (!userService.checkCredentials(singInRequest)) {
            return ResponseEntity.status(401).body(hashMapOf(Pair("reason", "No user with these credentials")))
        }

        return ResponseEntity.ok(hashMapOf(Pair("token", authService.createToken(singInRequest.login!!))))
    }
}
