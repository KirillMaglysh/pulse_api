package ru.prodcontest.db.reposetories

import org.springframework.data.jpa.repository.JpaRepository
import ru.prodcontest.db.entities.Post
import ru.prodcontest.db.entities.Reaction

interface ReactionRepository : JpaRepository<Reaction, Long> {
    fun save(reaction: Reaction)

    fun findFirstByLoginAndPostId(login: String, postId: Long): Reaction?
}
