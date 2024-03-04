package ru.prodcontest.services

import ru.prodcontest.db.entities.Post
import ru.prodcontest.requests.NewPostRequest

interface PostService {
    fun findPostById(id: Long, client: String): Post

    fun create(author: String, newPostRequest: NewPostRequest): Post
    fun getPosts(login: String, limit: Int, offset: Int): MutableList<Post>
    fun getPosts(clientLogin: String, ownerLogin: String, limit: Int, offset: Int): MutableList<Post>

    fun exists(id: Long): Boolean
    fun save(post: Post)
}