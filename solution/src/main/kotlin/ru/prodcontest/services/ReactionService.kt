package ru.prodcontest.services

import ru.prodcontest.db.entities.Post

interface ReactionService {
    fun react(postId: Long, clientLogin: String, isLike: Boolean): Post
}