package ru.prodcontest.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.prodcontest.db.exceptions.ApiException
import ru.prodcontest.db.exceptions.TokenException
import ru.prodcontest.services.AuthenticationService
import ru.prodcontest.services.UserService

@RestController
@RequestMapping("/api/profile")
class ProfilesController(
    @Autowired private val authService: AuthenticationService,
    @Autowired private val userService: UserService
) {
    @GetMapping("/{login}")
    fun getProfile(
        @RequestHeader(name = "Authorization") token: String,
        @PathVariable login: String
    ) : ResponseEntity<out Any>{
        return try {
            val client = authService.getTokenLogin(authService.parseToken(token))
            ResponseEntity.ok(userService.getProfile(client, login))
        } catch (e: TokenException) {
            ResponseEntity.status(401).body(hashMapOf(Pair("reason", e.message)))
        } catch (e: ApiException) {
            ResponseEntity.status(403).body(hashMapOf(Pair("reason", e.message)))
        }
    }
}