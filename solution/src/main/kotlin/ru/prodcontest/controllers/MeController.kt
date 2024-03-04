package ru.prodcontest.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.prodcontest.db.exceptions.ApiException
import ru.prodcontest.db.exceptions.TokenException
import ru.prodcontest.db.exceptions.WrongDBQueryArgumentException
import ru.prodcontest.db.exceptions.WrongOldPasswordException
import ru.prodcontest.requests.PatchProfileRequest
import ru.prodcontest.requests.UpdatePasswordRequest
import ru.prodcontest.services.AuthenticationService
import ru.prodcontest.services.UserService

@RestController
@RequestMapping("/api/me")
class MeController(
    @Autowired private val authService: AuthenticationService,
    @Autowired private val userService: UserService
) {
    @GetMapping("/profile")
    fun returnProfile(@RequestHeader(name = "Authorization") token: String): ResponseEntity<out Any> {
        return try {
            val login = authService.getTokenLogin(authService.parseToken(token))
            ResponseEntity.ok(userService.findWoPasswordByLogin(login))
        } catch (e: TokenException) {
            ResponseEntity.status(401).body(hashMapOf(Pair("reason", e.message)))
        }
    }

    @PatchMapping("/profile")
    fun patchProfile(
        @RequestHeader(name = "Authorization") token: String,
        @RequestBody patchProfileRequest: PatchProfileRequest
    ): ResponseEntity<out Any> {
        return try {
            val login = authService.getTokenLogin(authService.parseToken(token))
            userService.patch(login, patchProfileRequest)

            ResponseEntity.ok(userService.findWoPasswordByLogin(login))
        } catch (e: ApiException) {
            ResponseEntity.status(400).body(hashMapOf(Pair("reason", e.message)))
        } catch (e: TokenException) {
            ResponseEntity.status(401).body(hashMapOf(Pair("reason", e.message)))
        }
    }

    @PostMapping("/updatePassword")
    fun updatePassword(
        @RequestHeader(name = "Authorization") token: String,
        @RequestBody updatePasswordRequest: UpdatePasswordRequest
    ): ResponseEntity<out Any> {
        return try {
            val login = authService.getTokenLogin(authService.parseToken(token))
            userService.updatePassword(login, updatePasswordRequest)
            ResponseEntity.ok(hashMapOf(Pair("status", "ok")))
        } catch (e: TokenException) {
            ResponseEntity.status(401).body(hashMapOf(Pair("reason", e.message)))
        } catch (e: WrongDBQueryArgumentException) {
            ResponseEntity.status(400).body(hashMapOf(Pair("reason", e.message)))
        } catch (e: WrongOldPasswordException) {
            ResponseEntity.status(403).body(hashMapOf(Pair("reason", e.message)))
        }
    }
}
