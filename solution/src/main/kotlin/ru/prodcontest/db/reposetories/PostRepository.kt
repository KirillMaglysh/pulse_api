package ru.prodcontest.db.reposetories

import jakarta.persistence.Tuple
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.prodcontest.db.entities.Post

interface PostRepository : JpaRepository<Post, Long> {
    fun save(post: Post): Post

    @Query(
        value = "SELECT * from posts WHERE author=:log LIMIT :lim OFFSET :offs",
        nativeQuery = true
    )
    fun findAllByLogin(
        @Param("log") login: String,
        @Param("lim") lim: Int,
        @Param("offs") offs: Int
    ): MutableList<Tuple>
}
