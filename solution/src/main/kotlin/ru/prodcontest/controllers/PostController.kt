package ru.prodcontest.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.prodcontest.db.exceptions.ApiException
import ru.prodcontest.db.exceptions.TokenException
import ru.prodcontest.requests.NewPostRequest
import ru.prodcontest.services.AuthenticationService
import ru.prodcontest.services.PostService
import ru.prodcontest.services.ReactionService

@RestController
@RequestMapping("/api/posts")
class PostController(
    @Autowired private val authService: AuthenticationService,
    @Autowired private val postService: PostService,
    @Autowired private val reactionService: ReactionService
) {
    @PostMapping("/new")
    fun patchPost(
        @RequestHeader(name = "Authorization") token: String,
        @RequestBody newPostRequest: NewPostRequest
    ): ResponseEntity<out Any> {
        return try {
            val login = authService.getTokenLogin(authService.parseToken(token))
            ResponseEntity.ok(postService.create(login, newPostRequest).toSerial())
        } catch (e: TokenException) {
            ResponseEntity.status(401).body(hashMapOf(Pair("reason", e.message)))
        }
    }

    @GetMapping("/{postId}")
    fun getPost(
        @RequestHeader(name = "Authorization") token: String,
        @PathVariable(name = "postId") postId: Long
    ): ResponseEntity<out Any> {
        return try {
            val login = authService.getTokenLogin(authService.parseToken(token))
            ResponseEntity.ok(postService.findPostById(postId, login).toSerial())
        } catch (e: TokenException) {
            ResponseEntity.status(401).body(hashMapOf(Pair("reason", e.message)))
        } catch (e: ApiException) {
            ResponseEntity.status(404).body(hashMapOf(Pair("reason", e.message)))
        }
    }

    @PostMapping("/{postId}/like")
    fun likePost(
        @RequestHeader(name = "Authorization") token: String,
        @PathVariable(name = "postId") postId: Long
    ): ResponseEntity<out Any> {
        return try {
            val login = authService.getTokenLogin(authService.parseToken(token))
            ResponseEntity.ok(reactionService.react(postId, login, true).toSerial())
        } catch (e: TokenException) {
            ResponseEntity.status(401).body(hashMapOf(Pair("reason", e.message)))
        } catch (e: ApiException) {
            ResponseEntity.status(404).body(hashMapOf(Pair("reason", e.message)))
        }
    }

    @PostMapping("/{postId}/dislike")
    fun dislikePost(
        @RequestHeader(name = "Authorization") token: String,
        @PathVariable(name = "postId") postId: Long
    ): ResponseEntity<out Any> {
        return try {
            val login = authService.getTokenLogin(authService.parseToken(token))
            ResponseEntity.ok(reactionService.react(postId, login, false).toSerial())
        } catch (e: TokenException) {
            ResponseEntity.status(401).body(hashMapOf(Pair("reason", e.message)))
        } catch (e: ApiException) {
            ResponseEntity.status(404).body(hashMapOf(Pair("reason", e.message)))
        }
    }
}
