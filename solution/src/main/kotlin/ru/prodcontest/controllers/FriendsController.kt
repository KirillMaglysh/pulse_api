package ru.prodcontest.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.prodcontest.db.entities.FriendRequestParams
import ru.prodcontest.db.exceptions.ApiException
import ru.prodcontest.db.exceptions.TokenException
import ru.prodcontest.requests.AddRemoveFriendRequest
import ru.prodcontest.services.AuthenticationService
import ru.prodcontest.services.FriendshipService

@RestController
@RequestMapping("/api/friends")
class FriendsController(
    @Autowired private val authService: AuthenticationService,
    @Autowired private val friendshipService: FriendshipService
) {
    @PostMapping("/add")
    fun addFriend(
        @RequestHeader(name = "Authorization") token: String,
        @RequestBody addFriendRequest: AddRemoveFriendRequest
    ): ResponseEntity<out Any> {
        return try {
            val login = authService.getTokenLogin(authService.parseToken(token))
            friendshipService.addFriend(login, addFriendRequest)
            ResponseEntity.ok(hashMapOf(Pair("status", "ok")))
        } catch (e: ApiException) {
            ResponseEntity.status(404).body(hashMapOf(Pair("reason", e.message)))
        } catch (e: TokenException) {
            ResponseEntity.status(401).body(hashMapOf(Pair("reason", e.message)))
        }
    }

    @PostMapping("/remove")
    fun removeFriend(
        @RequestHeader(name = "Authorization") token: String,
        @RequestBody removeFriendRequest: AddRemoveFriendRequest
    ): ResponseEntity<out Any> {
        return try {
            val login = authService.getTokenLogin(authService.parseToken(token))
            friendshipService.removeFriend(login, removeFriendRequest)
            ResponseEntity.ok(hashMapOf(Pair("status", "ok")))
        } catch (e: TokenException) {
            ResponseEntity.status(401).body(hashMapOf(Pair("reason", e.message)))
        }
    }

    @GetMapping
    fun getFriends(
        @RequestHeader(name = "Authorization") token: String,
        @RequestParam(required = false) limit: Int = 5,
        @RequestParam(required = false) offset: Int = 0
    ): ResponseEntity<out Any> {
        return try {
            val login = authService.getTokenLogin(authService.parseToken(token))
            ResponseEntity.ok(friendshipService.getFriends(login, FriendRequestParams(limit, offset)))
        } catch (e: TokenException) {
            ResponseEntity.status(401).body(hashMapOf(Pair("reason", e.message)))
        }
    }
}
