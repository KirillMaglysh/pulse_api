package ru.prodcontest.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.prodcontest.db.entities.Post
import ru.prodcontest.db.exceptions.ApiException
import ru.prodcontest.db.exceptions.TokenException
import ru.prodcontest.services.AuthenticationService
import ru.prodcontest.services.PostService

@RestController
@RequestMapping("/api/posts/feed")

class FeedController(
    @Autowired private val authService: AuthenticationService,
    @Autowired private val postService: PostService
) {

    @GetMapping("/my")
    fun getMyPosts(
        @RequestHeader(name = "Authorization") token: String,
        @RequestParam(required = false) limit: Int = 5,
        @RequestParam(required = false) offset: Int = 0
    ): ResponseEntity<out Any> {
        return try {
            val login = authService.getTokenLogin(authService.parseToken(token))
            val result = ArrayList<Post.SerialPost>()
            postService.getPosts(login, limit, offset).forEach { result.add(it.toSerial()) }
            ResponseEntity.ok(result)
        } catch (e: TokenException) {
            ResponseEntity.status(401).body(hashMapOf(Pair("reason", e.message)))
        } catch (e: ApiException) {
            ResponseEntity.status(404).body(hashMapOf(Pair("reason", e.message)))
        }
    }

    @GetMapping("/{login}")
    fun getSomeonePosts(
        @RequestHeader(name = "Authorization") token: String,
        @RequestParam(required = false) limit: Int = 5,
        @RequestParam(required = false) offset: Int = 0,
        @PathVariable(name = "login") login: String
    ): ResponseEntity<out Any> {
        return try {
            val client = authService.getTokenLogin(authService.parseToken(token))
            val result = ArrayList<Post.SerialPost>()
            postService.getPosts(client, login, limit, offset).forEach { result.add(it.toSerial()) }
            ResponseEntity.ok(result)
        } catch (e: TokenException) {
            ResponseEntity.status(401).body(hashMapOf(Pair("reason", e.message)))
        } catch (e: ApiException) {
            ResponseEntity.status(404).body(hashMapOf(Pair("reason", e.message)))
        }
    }
}